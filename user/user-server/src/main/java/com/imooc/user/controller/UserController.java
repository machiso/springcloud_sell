package com.imooc.user.controller;

import com.imooc.order.VO.Response;
import com.imooc.order.VO.ResponseUtil;
import com.imooc.order.enums.ResponseEnum;
import com.imooc.order.enums.RoleEnum;
import com.imooc.user.constant.Constant;
import com.imooc.user.constant.RedisConstant;
import com.imooc.user.model.User;
import com.imooc.user.service.UserService;
import com.imooc.user.util.CookieUtil;
import com.netflix.client.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private UserService userService;
    @Autowired private StringRedisTemplate stringRedisTemplate;

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

    //cookie里设置token=UUID, redis设置key=UUID, value=xyz
    @RequestMapping("/seller")
    public Response seller(@RequestParam("openid") String openid,
                           HttpServletRequest request,HttpServletResponse response){
        //判断是否已经登录
        Cookie cookie = CookieUtil.get(request, Constant.TOKEN);
        if (cookie != null && !StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN,cookie.getValue())))){
            return ResponseUtil.success();
        }
        User user = userService.findUserByOpenid(openid);
        if (user == null){
            return ResponseUtil.error(ResponseEnum.LOGIN_FAIL);
        }
        if (user.getRole()!=RoleEnum.SELLER.getCode()){
            return ResponseUtil.error(ResponseEnum.ROLE_ERROR);
        }
        //cookie里设置token=UUID, redis设置key=UUID, value=xyz
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN,token),openid,Constant.expire,TimeUnit.SECONDS);

        CookieUtil.setCookie(response,Constant.TOKEN,token,Constant.expire);
        return ResponseUtil.success();
    }
}
