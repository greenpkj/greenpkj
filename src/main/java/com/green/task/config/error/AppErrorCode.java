package com.green.task.config.error;

import lombok.Getter;

@Getter
public enum AppErrorCode {

    UNKNOW_ERROR("APP_0001", "UNKNOW_ERROR"),
    METHOD_ARGUMENT_NOT_VALID("APP_0002", "METHOD_ARGUMENT_NOT_VALID"),
    METHOD_NOT_SUPPORT("APP_0003", "METHOD_NOT_SUPPORT"),

    NO_HANDLER_FOUND("APP_0004", "NO_HANDLER_FOUND"),
    CHECK_QUERY_STRING("APP_0005", "CHECK_QUERY_STRING"),
    CHECK_QUERY_SORT_OPTION("APP_0006", "CHECK_QUERY_SORT_OPTION"),
    ;
    private String code;
    private String message;

    private AppErrorCode(String code, String message) {
        this.code = code;

        this.message = message;
    }
}
