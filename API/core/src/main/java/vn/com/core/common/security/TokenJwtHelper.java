package vn.com.core.common.security;

import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.rsa.RSAVerifier;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TokenJwtHelper {
    private static final Logger logger = LoggerFactory.getLogger(TokenJwtHelper.class);
    protected final String SECRET = "e29c3643bf9cabe44e4ea5a9f8340379";//"SecretKeyFisFTU"
    public static final String REQUEST_PAIR = "request_pair";
    protected final String PUBLIC_KEY_FILE = "public_key.pem";

    private static TokenJwtHelper instance;

    public static TokenJwtHelper getInstance(){
        if (instance==null){
            instance = new TokenJwtHelper();
        }
        return instance;
    }

    protected String getStringKey(String keyName) {
        ClassPathResource cpr = new ClassPathResource(keyName);
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            return new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.warn("IOException", e);
        }
        return "";
    }

    public Boolean validateTokenRSA(String authToken) {
        try {
            Verifier verifier = RSAVerifier.newVerifier(getStringKey(PUBLIC_KEY_FILE));
            JWT.getDecoder().decode(authToken, verifier);
            return true;
        } catch (Exception e) {
            logger.error("Invalid token: " + e.getMessage());
        }
        return false;
    }

    public Map<String, Object> getAllClaims(String token) {
        try {
            Verifier verifier = RSAVerifier.newVerifier(getStringKey(PUBLIC_KEY_FILE));
            JWT jwt = JWT.getDecoder().decode(token, verifier);
            return jwt.getAllClaims();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new HashMap<>();
    }

    public String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }

    protected Date getExpirationDateFromToken(String token, boolean useRSA) {
        if (useRSA) {
            return Date.from(((ZonedDateTime) getAllClaims(token).get(Claims.EXPIRATION)).toInstant());
        } else {
            return getClaimFromToken(token, Claims::getExpiration);
        }
    }

    protected boolean isTokenExpired(String token, boolean useRSA) {
        final Date expiration = getExpirationDateFromToken(token, useRSA);
        return expiration.before(new Date());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    protected Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    protected boolean isValidToken(String authToken, boolean withExpireTime, boolean useRSA) {
        if (useRSA) {
            return (withExpireTime && isValidToken(authToken) && !isTokenExpired(authToken, useRSA))
                    || (!withExpireTime && isValidToken(authToken));
        } else {
            return (withExpireTime && isValidToken(authToken) && !isTokenExpired(authToken, useRSA))
                    || (!withExpireTime && isValidToken(authToken));
        }
    }

    public boolean isValidToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            logger.error("Invalid token >>> Cannot parse secret-key token - " + e.getMessage());
        }
        return false;
    }

    public String getSubjectToken(String token){
        try {
            Verifier verifier = RSAVerifier.newVerifier(getStringKey(PUBLIC_KEY_FILE));
            return JWT.getDecoder().decode(token, verifier).subject;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }
}
