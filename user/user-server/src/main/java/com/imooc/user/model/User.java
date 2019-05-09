package com.imooc.user.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String openid;
    // 1 卖家 2 卖家
    private Integer role;
}
