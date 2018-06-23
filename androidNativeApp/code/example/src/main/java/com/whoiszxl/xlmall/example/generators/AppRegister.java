package com.whoiszxl.xlmall.example.generators;

import com.whoiszxl.xl.annotations.AppRegisterGenerator;
import com.whoiszxl.xl.wechat.templates.AppRegisterTemplate;

@AppRegisterGenerator(
        packageName = "com.whoiszxl.xlmall.example",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
