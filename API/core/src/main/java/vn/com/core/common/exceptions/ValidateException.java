package vn.com.core.common.exceptions;

public class ValidateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;

    public ValidateException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ValidateException(Exception e, String message) {
        super(e);
        this.message = message;
    }

    public ValidateException(Exception e, String code, String message) {
        super(e);
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return getCode() + ": " + getMessage();
    }

    public String toString() {
        return getCode() + ": " + getMessage();
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
