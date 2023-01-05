package vn.com.core.common.utils;

import org.springframework.dao.DataAccessException;
import vn.com.core.common.config.CoreConstants;
import vn.com.core.common.config.ErrorCode;
import vn.com.core.common.exceptions.ValidateException;

public class ExceptionUtil {

    public static RuntimeException getDuplicateException() {
        return new ValidateException(ErrorCode.VALIDATE.DUPLICATE, "Duplicate");
    }

    public static RuntimeException getDefinedDuplicateException(String code, String duplicatedParam) {
        return new ValidateException(code, String.valueOf(duplicatedParam));
    }

    public static RuntimeException getDeleteChildFirstException() {
        return new ValidateException(ErrorCode.VALIDATE.FOREIGN_KEY, "Delete child first");
    }

    public static RuntimeException getRuntimException(RuntimeException ex) {
        try {
            if (ex instanceof DataAccessException) {
                String code = getErrorCode(ex);
                if (code.equals(CoreConstants.SYSTEM_ERROR_CODE.ORACLE_UNIQUE_CONSTRAINT_VIOLATED)) {
                    return getDuplicateException();
                }
                if (code.equals(CoreConstants.SYSTEM_ERROR_CODE.ORACLE_MAX_LENGTH)) {
                    return new ValidateException(ErrorCode.VALIDATE.MAX_LENGTH, getErrorMessage(ex));
                }
                if (code.equals(CoreConstants.SYSTEM_ERROR_CODE.ORACLE_INVALID_ROWID)) {
                    return new ValidateException(ErrorCode.VALIDATE.INVALID_DATA, getErrorMessage(ex));
                }
            }
        } catch (Exception e) {
            return ex;
        }

        return ex;
    }

    private static String getErrorCode(Exception ex) {
        String exMsg = ex.getCause().getCause().getMessage();
        return exMsg.substring(0, exMsg.indexOf(":"));
    }

    private static String getErrorMessage(Exception ex){
        return ex.getCause().getCause().getMessage();
    }
}
