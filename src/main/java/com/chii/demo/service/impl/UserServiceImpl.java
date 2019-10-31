package com.chii.demo.service.impl;

import com.chii.demo.mapper.UserMapper;
import com.chii.demo.pojo.User;
import com.chii.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserInfoByUsername(String username) {
        return  userMapper.selectByPrimaryKey(username);
    }
}
