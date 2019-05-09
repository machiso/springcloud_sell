package com.imooc.user.repository;

import com.imooc.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findUserByOpenid(String openid);
}
