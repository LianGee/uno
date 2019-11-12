package com.bigdata.uno.api.aop;

import com.bigdata.uno.api.util.ApiMethod;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CutPoints {
    @Pointcut(value = "within(com.bigdata.uno.api.controller.*) "
            + "&& @annotation(apiConfig)", argNames = "apiConfig")
    public void apiCall(ApiMethod apiConfig) {
    }
}
