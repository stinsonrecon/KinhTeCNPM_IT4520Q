package vn.com.core.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import vn.com.core.common.config.ErrorCode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagesResponse<T> {

    public interface RESPONSE_STATUS {
        String SUCCESS = "SUCCESS";
        String ERROR = "ERROR";
        String ERROR_WITH_PAR = "ERROR_WITH_PAR";
    }

    private String code;
    private String[] paramCode;
    private String message;
    private String status;
    private T data;

    public MessagesResponse(){
        this.code = ErrorCode.SUCCESS;
        this.status = RESPONSE_STATUS.SUCCESS;
    }

    public MessagesResponse<T> error(Exception e){
        this.setCode(ErrorCode.SYSTEM_ERROR);
        this.setStatus(RESPONSE_STATUS.ERROR);
        this.setMessage(e.toString());
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String[] getParamCode() {
        return paramCode;
    }

    public void setParamCode(String[] paramCode) {
        this.paramCode = paramCode;
    }
}
