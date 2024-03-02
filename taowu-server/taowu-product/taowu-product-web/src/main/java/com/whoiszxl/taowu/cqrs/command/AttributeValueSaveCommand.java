package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 属性值表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@Schema(description = "属性值表")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeValueSaveCommand implements Serializable {

    @Schema(description = "属性key主键id")
    private Long keyId;

    @Schema(description = "属性值")
    private String value;

}
