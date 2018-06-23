package com.whoiszxl.xlmall.example.generators;

import com.whoiszxl.xl.annotations.EntryGenerator;
import com.whoiszxl.xl.wechat.templates.WXEntryTemplate;

@EntryGenerator(
        packageName = "com.whoiszxl.xlmall.example",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
