package com.whoiszxl.cqrs.dto;

import lombok.Data;

/**
 * spu下的属性item
 *
 * @author whoiszxl
 * @date 2022/4/7
 */
@Data
public class SpuAttrDTO {

    private Long keyId;

    private String key;

    private Long valueId;

    private String value;

}
