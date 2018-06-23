package com.whoiszxl.xlmall.example.generators;

import com.whoiszxl.xl.annotations.PayEntryGenerator;
import com.whoiszxl.xl.wechat.templates.WXPayEntryTemplate;

@PayEntryGenerator(
        packageName = "com.whoiszxl.xlmall.example",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
