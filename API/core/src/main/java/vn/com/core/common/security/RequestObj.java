package vn.com.core.common.security;

public class RequestObj {
    String userId;
    String sessionId;
    String requestPair;
    String appCode;
    String clientIp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRequestPair() {
        return requestPair;
    }

    public void setRequestPair(String requestPair) {
        this.requestPair = requestPair;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
