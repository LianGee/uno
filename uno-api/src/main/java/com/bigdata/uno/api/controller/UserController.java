package com.bigdata.uno.api.controller;

import com.alibaba.fastjson.JSON;
import com.bigdata.uno.api.util.ApiMethod;
import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.common.model.cas.CasValidation;
import com.bigdata.uno.common.model.user.User;
import com.bigdata.uno.common.util.HttpUtil;
import com.bigdata.uno.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @Value("${cas.server-url-prefix}")
    private volatile String CAS_URL;

    @ApiMethod(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestBody User user) {
        return Response.success(userService.register(user));
    }

    @ApiMethod(value = "/query/all", method = RequestMethod.GET, requireLogin = false)
    public Response getAll() {
        return Response.success(userService.queryAll());
    }

    @ApiMethod(value = "/query/{id}", method = RequestMethod.GET)
    public Response getUserById(@PathVariable(value = "id") Long id) {
        return Response.success(userService.queryById(id));
    }

    @ApiMethod(value = "/login", method = RequestMethod.GET, requireLogin = false)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer service = request.getRequestURL();
        String ticket = request.getQueryString();
        String res = HttpUtil.get(String.format("%s/p3/serviceValidate?format=json&service=%s&%s", CAS_URL, service.toString(), ticket));
        CasValidation resp = JSON.parseObject(res, CasValidation.class);
        assert  resp.getServiceResponse().getAuthenticationSuccess().getUser() != null;
        log.info("login:{}", request.getSession().getId());
        request.getSession().setAttribute("user", resp.getServiceResponse().getAuthenticationSuccess().getUser());
        response.sendRedirect("http://localhost:8000/");
    }

    @ApiMethod(value = "/current", method = RequestMethod.GET)
    public Response current() {
        return Response.success(userService.queryByName(loginUtil.getLoginUser()));
    }
}
