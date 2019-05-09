package com.imooc.user.service;

import com.imooc.user.model.User;

public interface UserService {
    User findUserByOpenid(String openid);
}
