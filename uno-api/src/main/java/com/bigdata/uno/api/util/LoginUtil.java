package com.bigdata.uno.api.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginUtil {
    @Autowired
    private HttpServletRequest request;

    public String getLoginUser() {
        if (request.getUserPrincipal() != null) {
            String name = request.getUserPrincipal().toString();
            if (!StringUtils.isBlank(name)) {
                return name;
            }
        }
//        String appKey = request.getHeader(Constant.GRANDET_APP_KEY);
//        if (!StringUtils.isBlank(appKey)) {
//            return appKey;
//        }
        return "bchen";
    }
}
