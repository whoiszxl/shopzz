package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * SPU与属性key关联表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@TableName("pms_spu_key")
@ApiModel(value = "SpuKey对象", description = "SPU与属性key关联表")
public class SpuKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("SPU与属性key关联表主键id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("SPU ID")
    private Long spuId;

    @ApiModelProperty("属性key主键id")
    private Long keyId;


}
