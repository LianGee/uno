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
        String userString = (String) request.getSession().getAttribute("user");
        if (userString != null) {
            User user = JSON.parseObject(userString, User.class);
            return user.getName();
        }
//        if (request.getUserPrincipal() != null) {
//            String name = request.getUserPrincipal().toString();
//            if (!StringUtils.isBlank(name)) {
//                return name;
//            }
//        }
//        String appKey = request.getHeader(Constant.GRANDET_APP_KEY);
//        if (!StringUtils.isBlank(appKey)) {
//            return appKey;
//        }
        return null;
    }
}
