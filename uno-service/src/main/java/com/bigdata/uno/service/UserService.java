package com.bigdata.uno.service;

import com.bigdata.uno.common.model.user.User;

import java.util.List;

public interface UserService {
    Long register(User user);

    User getUserById(Long id);

    List<User> getAllUsers();
}
