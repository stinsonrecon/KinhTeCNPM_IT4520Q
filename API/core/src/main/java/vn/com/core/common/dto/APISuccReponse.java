package vn.com.core.common.dto;

import vn.com.core.common.config.ErrorCode;

public class APISuccReponse extends APIResponse {

    public APISuccReponse() {
        setCode(ErrorCode.SUCCESS);
    }
}
