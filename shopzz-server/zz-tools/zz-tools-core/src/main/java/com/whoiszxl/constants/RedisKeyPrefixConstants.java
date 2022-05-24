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


    public static final String APP_BANNER = "app:banner";
    public static final String COLUMN_DETAIL = "column:detail:";
    public static final String ACTIVITY_DETAIL = "activity:detail:";
    public static final String ACTIVITY_COUPONLIST = "activity:couponList:";
    public static final String ACTIVITY_NOTLIMIT_COUPONLIST = "activity:notlimitCouponList";
    public static final String CATEGORY_TREE = "category:tree";

    public static final String HOME_RECOMMEND_A = "home:recommend:a";
    public static final String HOME_RECOMMEND_B = "home:recommend:b";


    public static final String SECKILL_LOCK_ORDER_SUBMIT = "seckill:member:order:submit:";

    public static final String REPEAT_SECKILL_PLACE_ORDER = "repeat:seckill:place_order:";

    public static final String CACHE_SECKILL = "cache:seckill:";
    public static final String CACHE_SECKILL_LIST = "cache:seckill_list";
    public static final String CACHE_SECKILL_ITEM = "cache:seckill_item:";
    public static final String CACHE_SECKILL_ITEM_LIST = "cache:seckill_item_list";
    public static final String CACHE_ITEM_STOCK = "cache:item_stock:";
    public static final String CACHE_ITEM_STOCK_ALIGN = "cache:item_stock_align:";

    public static final String TOKEN_SECKILL_PLACE_ORDER = "token:seckill:place_order:";

    public static final String TASK_SECKILL_PLACE_ORDER_MQ = "task:seckill:place_order_mq:";
    public static final String TASK_SECKILL_PLACE_ORDER_MQ_ORDER_ID = "task:seckill:place_order_mq:order_id:";

    public static final String LOCK_GET_ITEM_FROM_DB = "lock:getItemFromDB:";
    public static final String LOCK_GET_SECKILL_FROM_DB = "lock:getSeckillFromDB:";
    public static final String LOCK_GET_SECKILL_LIST_FROM_DB = "lock:getSeckillListFromDB:";
    public static final String LOCK_GET_SECKILL_ITEM_LIST_FROM_DB = "lock:getSeckillItemListFromDB:";
    public static final String LOCK_SECKILL_REFRESH_PLACE_ORDER_TOKEN = "lock:seckill:refreshPlaceOrderToken:";




}
