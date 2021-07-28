package com.whoiszxl.constants;

/**
 * 优惠券常量
 *
 * @author whoiszxl
 * @date 2021/7/28
 */
public class CouponConstants {


    public static interface GiveType {

        public Integer CAN_GIVE_CAN_RECEIVE = 1;

        public Integer ONLY_GIVE = 2;

        public Integer ONLY_RECEIVE = 3;

    }

    public static interface Status {

        public Integer NOT_START = 1;

        public Integer GIVING = 2;

        public Integer GIVIED = 3;

        public Integer EXPIRED = 4;
    }

}
