package com.whoiszxl.common.utils;

import java.util.UUID;

/**
 * 随机UUID工具
 * 效果：b5e8863950d649ffa5a372dd0e951416
 * @author scq
 *
 */
public class RandomUuidUtil {

    public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }

    //public static void main(String[] args) {
    //    System.out.println(getUUID());
    //}
}
