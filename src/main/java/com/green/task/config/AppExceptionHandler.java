package com.green.task.config;

import com.green.task.config.error.ErrorResponse;
import com.green.task.config.exception.AppCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import static com.green.task.config.error.AppErrorCode.*;


@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ErrorResponse handleRuntime(HttpServletRequest req, Exception e) {

        log.error(e.getMessage(), e);
        return new ErrorResponse("check the error code", UNKNOW_ERROR.getCode(), UNKNOW_ERROR.getMessage());
    }


    //CustomException 처리
    @ExceptionHandler(AppCustomException.class)
    public final ResponseEntity<ErrorResponse> handlerCustomError(
            HttpServletRequest req, AppCustomException e) {

        ErrorResponse errorResponse =
                new ErrorResponse("check the error code", e.getErrReason(), e.getErrCode());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorResponse handleConstraintViolation(HttpServletRequest req, ConstraintViolationException e) {
        return new ErrorResponse(e.getMessage(), CHECK_QUERY_STRING.getCode(), CHECK_QUERY_STRING.getMessage());
    }


    //request param이 존재하지 않는 경우
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorResponse handleMissingServletRequestParameterException(HttpServletRequest req, MissingServletRequestParameterException e) {
        return new ErrorResponse(e.getMessage(), CHECK_QUERY_STRING.getCode(), CHECK_QUERY_STRING.getMessage());
    }

    //http method 지원하지 않을 경우
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public final ErrorResponse handleHttpRequestMethodNotSupportedException(HttpServletRequest req, HttpRequestMethodNotSupportedException e) {
        return new ErrorResponse(e.getMessage(), METHOD_NOT_SUPPORT.getCode(), METHOD_NOT_SUPPORT.getMessage());
    }

    //핸들러 매핑에 대응하는 컨트롤러를 찾지 못했을 경우
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleNoHandler(HttpServletRequest req, NoHandlerFoundException e) {
        return new ErrorResponse(e.getMessage(), NO_HANDLER_FOUND.getCode(), NO_HANDLER_FOUND.getMessage());
    }

    //요청 본문(HTTP POST/PUT/DELETE ...)에 대한 Validation 에러 핸들링
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorResponse handleValidation(
            HttpServletRequest req, MethodArgumentNotValidException e) {
        return new ErrorResponse(
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                METHOD_ARGUMENT_NOT_VALID.getCode(),
                METHOD_ARGUMENT_NOT_VALID.getMessage());
    }


}
