package vn.com.core.common.exceptions;

public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;

    public AppException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public AppException(Exception e, String message) {
        super(e);
        this.message = message;
    }

    public AppException(Exception e, String code, String message) {
        super(e);
        this.code = code;
        this.message = message;
    }

    public AppException withErrorCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return getCode() + ": " + this.message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
