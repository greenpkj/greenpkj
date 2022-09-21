package com.green.task.config.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

    public ErrorResponse(String errReason, String errCode, String errMsg) {
        this.errReason = errReason;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private String errReason;
    private String errCode;
    private String errMsg;
}

