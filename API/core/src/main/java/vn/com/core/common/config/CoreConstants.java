package vn.com.core.common.config;

public interface CoreConstants {
    String API_LOGIN = "/login";

    interface PRIVILEGE {
        String UPDATE = "UPDATE";
        String INSERT = "INSERT";
        String DELETE = "DELETE";
        String VIEW = "VIEW";
    }

    interface MESSAGE {
        String SUCCESS = "Success";
        String CREAT_SUCCESS = "Create success";
        String UPDATE_SUCCESS = "Update success";
        String DELETE_SUCCESS = "Update success";
        String USER_NOT_FOUND = "User not found";
        String AUTHEN_FAIL = "Authen fail";
        String INVALID_REFRESH_TOKEN = "Invalid refresh token";
    }

    interface SYSTEM_ERROR_CODE {

        String ORACLE_UNIQUE_CONSTRAINT_VIOLATED = "ORA-00001";
        String ORACLE_MAX_LENGTH = "ORA-12899";
        String ORACLE_INVALID_ROWID = "ORA-01410";
    }
}
