package com.whoiszxl.common;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * 
 * @author whoiszxl
 *
 */
public class Const {
	
	public static final String CURRENT_USER = "currentUser";
	
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";
	
	public static final String TOKEN_PREFIX = "token_";
	
	public interface RedisCacheExtime {
		/**
		 * 以秒为单位
		 */
		int REDIS_SESSION_EXTIME = 60*30;
	}
	
	public interface JWTTokenCache {
		
		/**
		 * 以毫秒为单位
		 */
		long JWT_TOKEN_EXTIME = 720000000;
	}
	
	public interface REDIS_LOCK {
		String CLOSE_ORDER_TASK_LOCK = "CLOSE_ORDER_TASK_LOCK";
	}
	
	public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }
	
	public interface Cart {
		int CHECKED = 1;//购物车选中
		int UN_CHECKED = 0;//购物车未选中
		
		String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
		String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
	}
	
	public enum ProductStatusEnum{
        ON_SALE(1,"在线");
        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
	
	public enum OrderStatusEnum{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭");


        OrderStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }
	
	public interface  AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }



    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝");

        PayPlatformEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
    
    public enum PaymentTypeEnum{
    	ONLINE_PAY(1,"在线支付");
    	;
    	PaymentTypeEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
        
        public static PaymentTypeEnum codeOf(int code){
            for(PaymentTypeEnum paymentTypeEnum : values()){
                if(paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }
	
	public interface Role{
		int ROLE_CUSTOMER = 0;//普通用户
		int ROLE_ADMIN = 1;//管理员
	}
	
	public interface ShiroRole {
		String ROLE_CUSTOMER = "0";//普通用户
		String ROLE_ADMIN = "1";//管理员
	}
	
	public interface Article {
		int BANNER_LIST_COUNT = 5; //banner轮播图数量
	}
}
