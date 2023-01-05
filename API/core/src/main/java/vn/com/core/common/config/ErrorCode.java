package vn.com.core.common.config;

public interface ErrorCode {
    String SUCCESS = "API-000";
    String SYSTEM_ERROR = "API-001";
    String LOGIC_ERROR = "API-002";

    interface APP {
        String EXCEPTION = "APP-001";
        String API_NOT_CONFIG = "APP-002";
    }
    interface AUTHENTICATE {
        String AUTHEN_FAIL = "AUT-001";
        String USER_NOT_FOUND = "AUT-002";
        String INVALID_REFRESH_TOKEN = "AUT-003";
        String GET_SITE_MAP_ERROR = "AUT-004";
    }
    interface VALIDATE {
        String EXCEPTION = "VALID-001";
        String DUPLICATE = "VALID-002";
        String NOT_FOUND = "VALID-003";
        String INVALID_DATA = "VALID-004";
        String MAX_LENGTH = "VALID-005";
        String FOREIGN_KEY = "VALID-006";
        String NULL_OBJ = "VALID-007";
    }
}
