package com.whoiszxl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppStart {

    /** 入口， install:安装后进入 icon:点击图标 点击通知:notice */
    private String entry;

    /** 开屏广告ID */
    private Long openAdId;

    /** 开屏广告持续时间 ms */
    private Long openAdDuration;

    /** 开屏广告点击跳过的时间 ms */
    private Long openAdSkipTime;


}
