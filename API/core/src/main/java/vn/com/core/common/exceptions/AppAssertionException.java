package vn.com.core.common.exceptions;

import vn.com.core.common.config.ErrorCode;

public class AppAssertionException extends AppException {
    private static final long serialVersionUID = 1L;

    public AppAssertionException(String messages) {
        super(ErrorCode.VALIDATE.NULL_OBJ, messages);
    }
}
