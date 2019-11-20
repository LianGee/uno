package com.bigdata.uno.api.util;

import com.alibaba.fastjson.JSON;
import com.bigdata.uno.common.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginUtil {
    @Autowired
    private HttpServletRequest request;

    public String getLoginUser() {
        String userString = request.isRequestedSessionIdValid() ? (String) request.getSession().getAttribute("user") : null;
        User user = JSON.parseObject(userString, User.class);
        return user == null ? null : user.getName();
    }
}
