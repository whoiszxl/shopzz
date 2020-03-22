package com.whoiszxl.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("mall_ad")
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 广告名称
     */
    private String name;

    /**
     * 广告位置
     */
    private String position;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 到期时间
     */
    private LocalDateTime endTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 图片地址
     */
    private String image;

    /**
     * URL
     */
    private String url;

    /**
     * 备注
     */
    private String remarks;


}
