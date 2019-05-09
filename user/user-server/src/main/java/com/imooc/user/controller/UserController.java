package com.imooc.user.controller;

import com.imooc.order.VO.Response;
import com.imooc.order.VO.ResponseUtil;
import com.imooc.order.enums.ResponseEnum;
import com.imooc.order.enums.RoleEnum;
import com.imooc.user.constant.Constant;
import com.imooc.user.model.User;
import com.imooc.user.service.UserService;
import com.imooc.user.util.CookieUtil;
import com.netflix.client.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private UserService userService;

    @RequestMapping("/buyer")
    public Response buyer(@RequestParam("openid") String openid, HttpServletResponse response){
        User user = userService.findUserByOpenid(openid);
        if (user == null){
            return ResponseUtil.error(ResponseEnum.LOGIN_FAIL);
        }
        if (user.getRole()!=RoleEnum.BUYER.getCode()){
            return ResponseUtil.error(ResponseEnum.ROLE_ERROR);
        }
        //设置cookie cookie里设置openid=abc
        CookieUtil.setCookie(response,Constant.OPEN_ID,openid,Constant.expire);
        return ResponseUtil.success();
    }

    @RequestMapping("/seller")
    public String seller(){
        return null;
    }
}
