package com.whoiszxl.taowu.cqrs.vo;

import com.whoiszxl.taowu.cqrs.dto.SpuAttrDTO;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "SPU属性组返回VO实体")
public class SpuAttributeGroupVO {

    @Schema(description = "key Id")
    private Long keyId;

    @Schema(description = "key 名称")
    private String keyName;

    @Schema(description = "默认选中第几个属性")
    private Integer selected = 0;

    @Schema(description = "属性列表")
    private List<SpuAttrDTO> spuAttrList;

}
