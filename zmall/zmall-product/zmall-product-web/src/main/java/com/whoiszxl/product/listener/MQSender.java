package com.whoiszxl.product.listener;

public interface MQSender {

    void send(String message, String tag);
}
