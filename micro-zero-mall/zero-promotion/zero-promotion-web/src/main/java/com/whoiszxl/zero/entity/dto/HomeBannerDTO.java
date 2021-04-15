package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 首页轮播表(PromotionHomeBanner)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
public class HomeBannerDTO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = -98477268373070661L;
    /**
    * 自增主键ID
    */
    private Integer id;
    /**
    * 轮播图名称
    */
    private String name;
    /**
    * 轮播位置：0->PC首页轮播；1->app首页轮播 2->app导航小组件
    */
    private Integer type;
    /**
    * 图片地址
    */
    private String pic;
    /**
    * 上下线状态：0->下线；1->上线
    */
    private Integer status;
    /**
    * 点击数
    */
    private Integer clickCount;
    /**
    * 链接地址
    */
    private String url;
    /**
    * 备注
    */
    private String note;
    /**
    * 排序
    */
    private Integer sort;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;

}