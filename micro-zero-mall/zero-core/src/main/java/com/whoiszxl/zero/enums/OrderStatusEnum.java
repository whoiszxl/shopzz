package com.whoiszxl.zero.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    WAIT_PAY(1,"待付款"),
    CANCLED(2,"已取消"),
    WAIT_SEND(3,"待发货"),
    WAIT_RECIVE(4,"待收货"),
    RECIEVED(5,"已完成"),


    SERVICING(6,"售后中"),
    SERVICED(7,"售后完成");

    private Integer code;
    private String desc;

}
