package com.whoiszxl.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("mall_provinces")
public class Provinces implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 省份ID
     */
    @TableId(value = "provinceid", type = IdType.ID_WORKER)
    private String provinceid;

    /**
     * 省份名称
     */
    private String province;


}
