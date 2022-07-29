package com.whoiszxl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppCommon {

    /** 设备ID */
    private String deviceId;

    /** 用户ID */
    private String uid;

    /** 版本号 */
    private String version;

    /** 应用渠道 */
    private String channel;

    /** 区域 */
    private String area;

    /** 手机型号 */
    private String model;

    /** 手机品牌 */
    private String brand;

    private String ip;

    private String latitude;

    private String longitude;

    private String net;

}
