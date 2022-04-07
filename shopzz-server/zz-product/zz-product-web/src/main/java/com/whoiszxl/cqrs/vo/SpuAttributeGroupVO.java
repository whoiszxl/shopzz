package com.whoiszxl.cqrs.vo;

import com.whoiszxl.cqrs.dto.SpuAttrDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * SPU属性组返回VO实体
 *
 * @author whoiszxl
 * @date 2022/4/7
 */
@Data
@AllArgsConstructor
@ApiModel("SPU属性组返回VO实体")
public class SpuAttributeGroupVO {

    @ApiModelProperty("key Id")
    private Long keyId;

    @ApiModelProperty("key 名称")
    private String keyName;

    @ApiModelProperty("属性列表")
    private List<SpuAttrDTO> spuAttrList;

}
