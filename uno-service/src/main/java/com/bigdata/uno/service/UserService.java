package com.bigdata.uno.service;

import com.bigdata.uno.common.model.user.LoginForm;
import com.bigdata.uno.common.model.user.User;

import java.util.List;

public interface UserService {
    Long register(User user);

    User queryById(Long id);

    List<User> queryAll();

    User login(LoginForm loginForm);
}
