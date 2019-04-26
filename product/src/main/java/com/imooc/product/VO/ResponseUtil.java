package com.imooc.product.VO;

public class ResponseUtil {
    public static Response success(Object object){
        Response response = new Response();
        response.setCode(0);
        response.setMsg("成功");
        response.setData(object);
        return response;
    }
}
