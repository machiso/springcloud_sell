package com.imooc.order.utils;

import java.util.Random;

public class KeyGenUnique {
    public static synchronized String genereteKeyUnique(){
        Random random = new Random();
        int num = random.nextInt(900000) + 100000;
        return System.currentTimeMillis()+String.valueOf(num);
    }
}
