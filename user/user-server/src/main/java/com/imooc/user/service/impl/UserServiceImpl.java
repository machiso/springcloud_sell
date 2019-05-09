package com.imooc.user.service.impl;

import com.imooc.user.model.User;
import com.imooc.user.repository.UserRepository;
import com.imooc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository userRepository;
    @Override
    public User findUserByOpenid(String openid) {
        return userRepository.findUserByOpenid(openid);
    }
}
