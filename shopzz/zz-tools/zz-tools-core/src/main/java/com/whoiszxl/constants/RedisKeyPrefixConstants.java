package com.whoiszxl.constants;

/**
 * Redis Key 前缀常量
 *
 * @author whoiszxl
 * @date 2021/12/7
 */
public class RedisKeyPrefixConstants {


    public static final String ADMIN_PRIVILEGE_PREFIX = "admin:privilege:";

    public static final String VIDEO_PREFIX = "video:";

    public static final String MEMBER_BLOOM_REGISTER_ID = "member:registerId";
    public static final Integer MEMBER_BLOOM_REGISTER_ID_SIZE = 100 * 10000;
    public static final double MEMBER_BLOOM_REGISTER_ID_FPP = 0.03;


    public static final String MEMBER_CART_PREFIX = "cart:";

}
