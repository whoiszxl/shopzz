package com.whoiszxl.taowu.common.constants;

/**
 * Redis Key 前缀常量
 *
 * @author whoiszxl
 */
public class RedisPrefixConstants {


    public interface Admin {
        String ADMIN_CAPTCHA_IMAGE_KEY = "admin:captcha:image:key";
    }

    public interface Member {

        String MEMBER_CAPTCHA_SMS = "member:captcha:sms";
        String MEMBER_CAPTCHA_SMS_SEPARATOR = "_";
        Integer SMS_TIMEOUT = 120;
        String VIDEO_PREFIX = "video:";

    }

    public interface Video {
        String VIDEO_PREFIX = "video:";
    }

    public interface Counter {
        String COUNTER_MEMBER_HASH_KEY = "counter:member";
        String COUNTER_VIDEO_HASH_KEY = "counter:video";
    }


    public interface Order {
        String MEMBER_CART_PREFIX = "cart:";
    }

    public interface Marketing {
        String BLOOM_SECKILL_ID = "bloom:seckillId";
        String CACHE_SECKILL = "cache:seckill:";
        String CACHE_SECKILL_LIST = "cache:seckill_list";
        String CACHE_SECKILL_ITEM = "cache:seckill_item:";
        String CACHE_SECKILL_ITEM_LIST = "cache:seckill_item_list:";
        String CACHE_ITEM_STOCK = "cache:item_stock:";
        String CACHE_ITEM_STOCK_ALIGN = "cache:item_stock_align:";

        String TOKEN_SECKILL_PLACE_ORDER = "token:seckill:place_order:";

        String TASK_SECKILL_PLACE_ORDER_MQ = "task:seckill:place_order_mq:";
        String TASK_SECKILL_PLACE_ORDER_MQ_ORDER_ID = "task:seckill:place_order_mq:order_id:";

        String LOCK_GET_ITEM_FROM_DB = "lock:getItemFromDB:";
        String LOCK_GET_SECKILL_FROM_DB = "lock:getSeckillFromDB:";
        String LOCK_GET_SECKILL_LIST_FROM_DB = "lock:getSeckillListFromDB:";
        String LOCK_GET_SECKILL_ITEM_LIST_FROM_DB = "lock:getSeckillItemListFromDB:";
        String LOCK_SECKILL_REFRESH_PLACE_ORDER_TOKEN = "lock:seckill:refreshPlaceOrderToken:";

        String APP_BANNER = "app:banner";
        String ACTIVITY_NOTLIMIT_COUPONLIST = "activity:notlimitCouponList";
        String COLUMN_DETAIL = "column:detail:";

        String HOME_RECOMMEND_A = "home:recommend:a";
        String HOME_RECOMMEND_B = "home:recommend:b";

        String SECKILL_LOCK_ORDER_SUBMIT = "seckill:member:order:submit:";

        String ACTIVITY_DETAIL = "activity:detail:";



    }

    public static String format(String... keys) {
        return String.join(":", keys);
    }

}
