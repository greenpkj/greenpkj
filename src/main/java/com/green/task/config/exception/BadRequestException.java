package com.green.task.config.exception;

import com.green.task.config.error.AppErrorCode;

public class BadRequestException extends AppCustomException {

    public BadRequestException(String errCode, String errReason) {
        super(400, errCode, errReason);
    }

    public BadRequestException(AppErrorCode errorStatusCode) {
        super(400, errorStatusCode);
    }
}
