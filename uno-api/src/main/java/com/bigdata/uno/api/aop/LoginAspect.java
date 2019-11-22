package com.bigdata.uno.api.aop;

import com.bigdata.uno.api.util.ApiMethod;
import com.bigdata.uno.api.util.LoginUtil;
import com.bigdata.uno.common.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(4)
@Slf4j
public class LoginAspect {

    @Autowired
    private LoginUtil loginUtil;

    @Value("${cas.server-url-prefix}")
    private volatile String CAS_URL;

    @Around(value = "com.bigdata.uno.api.aop.CutPoints.apiCall(apiConfig)", argNames = "joinPoint,apiConfig")
    public Object around(ProceedingJoinPoint joinPoint, ApiMethod apiConfig) throws Throwable {
        try {
            if (apiConfig.requireLogin()) {
                String user = loginUtil.getLoginUser();
                if (user == null) {
                    log.info("未登录");
                    return Response.builder()
                            .status(30200)
                            .msg("请登录")
                            .data(String.format("%s/login?service=%s",CAS_URL, "http://localhost:9577/user/login"))
                            .build();
                }
            }
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw e;
        }
    }
}
