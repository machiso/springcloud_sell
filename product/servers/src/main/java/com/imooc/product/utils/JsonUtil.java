package com.imooc.product.utils;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    public static String toJson(Object o){
        try {
            return JSONObject.toJSONString(o);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
