package com.whoiszxl.xl.delegates.web;

import com.alibaba.fastjson.JSON;

public class XLWebInterface {

    private final WebDelegate DELEGATE;

    public XLWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    static XLWebInterface create(WebDelegate delegate) {
        return new XLWebInterface(delegate);
    }

    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        return null;
    }
}
