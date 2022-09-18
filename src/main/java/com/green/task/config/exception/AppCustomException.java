package com.green.task.config.exception;

import com.green.task.config.error.AppErrorCode;
import lombok.Getter;

@Getter
public class AppCustomException extends RuntimeException {

    public AppCustomException(int statusCode, String errCode, String errReason) {

        this.statusCode = statusCode;
        this.errCode = errCode;
        this.errReason = errReason;
    }

    public AppCustomException(int statusCode, AppErrorCode errorCode) {

        this.statusCode = statusCode;
        this.errCode = errorCode.getCode();
        this.errReason = errorCode.getMessage();
    }

    private int statusCode;
    private String errCode;
    private String errReason;

}
