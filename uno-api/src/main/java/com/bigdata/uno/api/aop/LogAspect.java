package com.bigdata.uno.api.aop;

import com.alibaba.fastjson.JSON;
import com.bigdata.uno.api.util.ApiMethod;
import com.bigdata.uno.api.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Aspect
@Component
@Order(3)
@Slf4j
public class LogAspect {

    @Autowired
    private LoginUtil loginUtil;

    @Around(value = "com.bigdata.uno.api.aop.CutPoints.apiCall(apiConfig)", argNames = "joinPoint,apiConfig")
    public Object around(ProceedingJoinPoint joinPoint, ApiMethod apiConfig) throws Throwable {
        boolean success = true;
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            success = false;
            throw e;
        } finally {
            if (apiConfig.requireLog()) {
                long elapsed = System.currentTimeMillis() - startTime;
                HttpServletRequest request
                        = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
                String user = loginUtil.getLoginUser();
                user = user == null ? "-" : user;
                log.info("user: {}, uri: {}, query: {}, success: {}, elapsed: {}ms result: {}",
                        user, request.getRequestURI(), request.getQueryString(), success, elapsed, JSON.toJSONString(result, true));
            }
        }
    }
}
