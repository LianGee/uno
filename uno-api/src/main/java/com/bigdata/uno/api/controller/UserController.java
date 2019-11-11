package com.bigdata.uno.api.controller;

import com.bigdata.uno.common.model.Response;
import com.bigdata.uno.common.model.user.LoginForm;
import com.bigdata.uno.common.model.user.User;
import com.bigdata.uno.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestBody User user) {
        return Response.success(userService.register(user));
    }

    @RequestMapping(value = "/query/all", method = RequestMethod.GET)
    public Response getAll() {
        return Response.success(userService.queryAll());
    }

    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Response getUserById(@PathVariable(value = "id") Long id) {
        return Response.success(userService.queryById(id));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestBody LoginForm loginForm) {
        userService.login(loginForm);
        return Response.success(null);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Response current() {
        return Response.success(userService.queryById(1L));
    }
}
