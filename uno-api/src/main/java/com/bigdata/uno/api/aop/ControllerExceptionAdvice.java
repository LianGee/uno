package com.bigdata.uno.api.aop;

import com.bigdata.uno.common.constant.ErrorCode;
import com.bigdata.uno.common.exception.ServerException;
import com.bigdata.uno.common.exception.SystemException;
import com.bigdata.uno.common.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ Author     ：zaoshu.
 * @ Date       ：Created in 16:21 2018/12/18
 * @ Description：
 */
@ControllerAdvice
@Slf4j
@RestController
@RequestMapping(value = "error", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ControllerExceptionAdvice {

    private static final String CAT_STATE = "cat-state";

    private final HttpServletRequest req;

    @Autowired
    public ControllerExceptionAdvice(HttpServletRequest req) {
        this.req = req;
    }

    @ExceptionHandler(ServerException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Response handleServerException(ServerException ex) {
        req.setAttribute(CAT_STATE, ex.getClass().getName());
        log.error(ex.getMessage(), ex);
        return Response.fail(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(SystemException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleSystemException(SystemException ex) {
        req.setAttribute(CAT_STATE, ex.getClass().getName());
        log.error(ex.getMessage(), ex);
        return Response.fail(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response handleRuntimeException(RuntimeException ex) {
        req.setAttribute(CAT_STATE, ex.getClass().getName());
        log.error(ex.getMessage(), ex);
        return Response.fail(ErrorCode.SYSTEM_EXCEPTION.getCode(), "System Error");
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response handleAny(Throwable th) {
        req.setAttribute(CAT_STATE, th.getClass().getName());
        log.error(th.getMessage(), th);
        return Response.fail(ErrorCode.SYSTEM_EXCEPTION.getCode(), "System Error");
    }
}
