package com.bigdata.uno.service.impl;

import com.bigdata.uno.common.constant.Constant;
import com.bigdata.uno.common.exception.ServerException;
import com.bigdata.uno.common.model.user.LoginForm;
import com.bigdata.uno.common.model.user.User;
import com.bigdata.uno.common.util.Preconditions;
import com.bigdata.uno.repository.UserRepository;
import com.bigdata.uno.repository.base.Fields;
import com.bigdata.uno.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Long register(User user) {
        Preconditions.checkNotNull(user.getName(), "用户名不可为空");
        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword() + Constant.MD5_SALT).getBytes()));
        if (userRepository.selectOne(Fields.NAME.eq(user.getName())) != null) {
            throw new ServerException("name is deprecated");
        }
        if (userRepository.insert(user) > 0) {
            return userRepository.selectOne(Fields.NAME.eq(user.getName())).getId();
        }
        return null;
    }

    @Override
    public User queryById(Long id) {
        return userRepository.selectById(id);
    }

    @Override
    public List<User> queryAll() {
        return userRepository.selectAll();
    }

    @Override
    public User login(LoginForm loginForm) {
        User user = userRepository.selectOne(Fields.NAME.eq(loginForm.getUserName()));
        Preconditions.checkNotNull(user, "用户不存在");
        Preconditions.checkEqual(DigestUtils.md5DigestAsHex((loginForm.getPassword() + Constant.MD5_SALT).getBytes()),
                user.getPassword(),
                "用户名密码不匹配");
        return user;
    }

    @Override
    public List<User> queryByNames(List<String> names) {
        return userRepository.selectWhere(Fields.NAME.in(names));
    }

    @Override
    public User queryByName(String name) {
        return userRepository.selectOne(Fields.NAME.eq(name));
    }
}
