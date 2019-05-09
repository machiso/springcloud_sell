package com.imooc.order.VO;

import com.imooc.order.enums.ResponseEnum;

public class ResponseUtil {
    public static Response success(Object object){
        Response response = new Response();
        response.setCode(0);
        response.setMsg("成功");
        response.setData(object);
        return response;
    }

    public static Response success(){
        Response response = new Response();
        response.setCode(0);
        response.setMsg("成功");
        return response;
    }

    public static Response error(ResponseEnum responseEnum){
        Response response = new Response();
        response.setCode(responseEnum.getCode());
        response.setMsg(responseEnum.getMessage());
        return response;
    }
}
