package com.bigdata.uno.api.controller;

import com.alibaba.fastjson.JSON;
import com.bigdata.uno.api.util.ApiMethod;
import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.common.model.user.LoginForm;
import com.bigdata.uno.common.model.user.User;
import com.bigdata.uno.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiMethod(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestBody User user) {
        return Response.success(userService.register(user));
    }

    @ApiMethod(value = "/query/all", method = RequestMethod.GET)
    public Response getAll() {
        return Response.success(userService.queryAll());
    }

    @ApiMethod(value = "/query/{id}", method = RequestMethod.GET)
    public Response getUserById(@PathVariable(value = "id") Long id) {
        return Response.success(userService.queryById(id));
    }

    @ApiMethod(value = "/login", method = RequestMethod.POST, requireLogin = false)
    public Response login(@RequestBody LoginForm loginForm, HttpSession session) {
        User user = userService.login(loginForm);
        session.setAttribute("user", JSON.toJSONString(user));
        return Response.success(user);
    }

    @ApiMethod(value = "/current", method = RequestMethod.GET)
    public Response current() {
        return Response.success(userService.queryById(1L));
    }
}
