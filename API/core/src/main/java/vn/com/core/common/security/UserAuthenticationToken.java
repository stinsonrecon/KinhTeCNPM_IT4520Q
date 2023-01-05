package vn.com.core.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 420L;
    private final Object user;
    private RequestObj data;

    public UserAuthenticationToken(Object user, RequestObj data) {
        super((Collection) null);
        this.user = user;
        this.data = data;
        this.setAuthenticated(false);
    }

    public UserAuthenticationToken(Object user, RequestObj data, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        this.data = data;
        super.setAuthenticated(true);
    }

    public RequestObj getCredentials() {
        return this.data;
    }

    public Object getPrincipal() {
        return this.user;
    }

    public RequestObj getData() {
        return this.data;
    }

    public Object getUser() {
        return this.user;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.data = null;
    }
}
