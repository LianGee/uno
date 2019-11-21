package com.bigdata.uno.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginUtil {
    @Autowired
    private HttpServletRequest request;

    public String getLoginUser() {
        return request.getUserPrincipal().getName();
    }
}
