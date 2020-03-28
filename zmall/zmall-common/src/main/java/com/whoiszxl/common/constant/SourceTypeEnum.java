package com.whoiszxl.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-28
 **/
@AllArgsConstructor
@Getter
public enum SourceTypeEnum {

    WEB("1", "web端"),
    APP("2", "移动app端"),
    WEIXIN("3", "微信小程序端");

    @Setter
    private String source;

    @Setter
    private String nameCn;


}