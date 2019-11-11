package com.bigdata.uno.api.controller;

import com.bigdata.uno.common.model.Response;
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

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Response getAll() {
        return Response.success(userService.queryAll());
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Response getUserById(@PathVariable(value = "id") Long id) {
        return Response.success(userService.queryById(id));
    }
}
