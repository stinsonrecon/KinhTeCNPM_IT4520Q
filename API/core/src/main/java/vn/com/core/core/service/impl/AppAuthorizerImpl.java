package vn.com.core.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.com.core.common.annotation.ApiRequestMapping;
import vn.com.core.common.security.UserAuthenticationToken;
import vn.com.core.common.utils.StringUtil;
import vn.com.core.core.service.AppAuthorizer;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

@Service("appAuthorizer")
public class AppAuthorizerImpl implements AppAuthorizer {
    private final Logger logger = LoggerFactory.getLogger(AppAuthorizerImpl.class);

//    @Autowired
//    LogManager logManager;

    @Override
    public boolean authorize(Authentication authentication, String action, Object callerObj) {
        Annotation annotation = extractSecuredPath(callerObj);
        String securedPath = "";
        boolean logging = false;
        if (annotation instanceof ApiRequestMapping) {
            securedPath = ((ApiRequestMapping) annotation).module();
            logging = ((ApiRequestMapping) annotation).logging();
        } else if (annotation instanceof RequestMapping) {
            RequestMapping requestMapping = ((RequestMapping) annotation);
            if (requestMapping.value() != null && requestMapping.value().length > 0) {
                securedPath = requestMapping.value()[0];
            }
            logging = true;
        }
        logger.debug("Do authorize ====>  [resource: {}, action {}, user: {}]", securedPath, action, authentication.getName());
        if (StringUtil.stringIsNullOrEmty(securedPath)) {//login, logout
            return true;
        }
        boolean isAllow = true;
        try {
            if (!(authentication instanceof UserAuthenticationToken)) {
                return true;
            }
            UserAuthenticationToken user = (UserAuthenticationToken) authentication;
            if (user == null || user.getData() == null) {
                logger.warn("User info is empty!");
                return isAllow;
            }
            String userId = user.getData().getUserId();
            if (StringUtil.stringIsNullOrEmty(userId)) {
                logger.warn("User ID is empty!");
                return isAllow;
            }
            if (checkPermission(authentication, action)) {
                String sessionId = StringUtil.nvl(user.getData().getSessionId(), "");
                String requestPair = StringUtil.nvl(user.getData().getRequestPair(), "");
                String appCode = StringUtil.nvl(user.getData().getAppCode(), "");
                //logAction(appCode, sessionId, userId, requestPair, action, securedPath, logging);
                isAllow = true;
            }else{
                logger.warn("User does not have permission!");
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
            throw e;
        }
        return isAllow;
    }

    // Lay ra securedPath duoc Annotate trong entity
    private Annotation extractSecuredPath(Object callerObj) {
        Class<?> clazz = ResolvableType.forClass(callerObj.getClass()).getRawClass();
        Optional<Annotation> apiRequestMapping = Arrays.asList(clazz.getAnnotations()).stream()
                .filter((ann) -> ann instanceof ApiRequestMapping)
                .findFirst();
        Optional<Annotation> requestMapping = Arrays.asList(clazz.getAnnotations()).stream()
                .filter((ann) -> ann instanceof RequestMapping)
                .findFirst();
        logger.debug("FOUND CALLER CLASS: {}", ResolvableType.forClass(callerObj.getClass()).getType().getTypeName());
        if (apiRequestMapping.isPresent()) {
            return apiRequestMapping.get();
        } else if (requestMapping.isPresent()) {
            return requestMapping.get();
        }
        return null;
    }

    public boolean checkPermission(Authentication authentication, String action) {
//                Object obj = redisRepository.findPermission(Long.valueOf(userId));
//                Map<String, String> map = new com.fasterxml.jackson.databind.ObjectMapper().convertValue(obj, Map.class);
//                String privilege = map.get(menu);
//                if (privilege != null) {
//                    if (privilege.contains(action)) {
//                      return true;
//                    }
//                }
//                return false;
        return true;
    }

//    public void logAction(String appCode, String sessionId, String userId, String requestPair, String action, String menu, boolean logging) {
//        try {
//            logger.info("=========== BEGIN LOG ACTION ==============");
//            //Integer menuId = obj.getMenuId();
//            //Integer privilegeId = obj.getPrivilegeId();
//            logger.info("AppCode: " + appCode);
//            logger.info("SessionId: " + sessionId);
//            logger.info("UserId: " + userId);
//            logger.info("RequestPair: " + requestPair);
//            logger.info("Action: " + action);
//            logger.info("Menu: " + menu);
//            logger.info("DB Logging: " + logging);
//            if (logging) {
//                LogAction logAction = new LogAction();
//                logAction.setAppCode(appCode);
//                logAction.setSessionId(Integer.valueOf(sessionId));
//                logAction.setPrivilegeId(action);
//                logAction.setMenuId(menu);
//                logAction.setUserId(Integer.valueOf(userId));
//                logAction.setRequestPair(requestPair);
//                logAction.setCreateTime(new Date());
//                logManager.submit(logAction);
//            }
//            logger.info("=========== END LOG ACTION ==============");
//        } catch (Exception e) {
//            logger.error(e.toString(), e);
//        }
//    }

}
