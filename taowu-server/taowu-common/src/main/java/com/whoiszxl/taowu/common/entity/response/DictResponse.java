package com.whoiszxl.taowu.common.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * 字典返回实体
 * @author whoiszxl
 */
@Data
@Schema(description = "字典返回实体")
public class DictResponse<V> implements Serializable {

    @Schema(description = "标签")
    private String label;

    @Schema(description = "值")
    private V value;

    public DictResponse(String label, V value) {
        this.label = label;
        this.value = value;
    }
}
