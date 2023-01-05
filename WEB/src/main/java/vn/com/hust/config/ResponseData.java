package vn.com.hust.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    private String errorCode;
    private Boolean success;
    private Object content;

    public ResponseData(Object content) {
        this.content = content;
        this.success = true;
    }

    public ResponseData(String errorCode) {
        this.errorCode = errorCode;
        this.success = false;
    }

    public ResponseData(Boolean isSuccess, Object content) {
        this.content = content;
        this.success = isSuccess;
    }

    public ResponseData(Boolean isSuccess, String errorCode, Object content) {
        this.content = content;
        this.success = isSuccess;
        this.errorCode = errorCode;
    }
}
