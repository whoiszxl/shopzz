package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * SPU与属性key关联表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@TableName("pms_spu_key")
@Schema(description = "SPU与属性key关联表")
public class SpuKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "SPU与属性key关联表主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "SPU ID")
    private Long spuId;

    @Schema(description = "属性key主键id")
    private Long keyId;


}
