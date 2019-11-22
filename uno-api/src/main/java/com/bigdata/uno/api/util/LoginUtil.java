package com.bigdata.uno.api.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class LoginUtil {
    @Autowired
    private HttpServletRequest request;

    public String getLoginUser() {
        return (String) request.getSession().getAttribute("user");
    }
}
