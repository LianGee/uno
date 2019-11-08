package com.bigdata.uno.service.impl;

import com.bigdata.uno.common.exception.ServerException;
import com.bigdata.uno.common.model.user.User;
import com.bigdata.uno.repository.UserRepository;
import com.bigdata.uno.repository.base.Fields;
import com.bigdata.uno.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Long register(User user) {
        if (user.getId() != null) {
            throw new ServerException("id is not null");
        }
        if (userRepository.selectOne(Fields.NAME.eq(user.getName())) != null) {
            throw new ServerException("name is deprecated");
        }
        if (userRepository.insert(user) > 0) {
            return userRepository.selectOne(Fields.NAME.eq(user.getName())).getId();
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.selectById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.selectAll();
    }
}
