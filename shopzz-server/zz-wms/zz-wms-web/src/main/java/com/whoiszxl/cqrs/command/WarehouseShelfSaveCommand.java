package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * WMS仓库货架表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Data
@ApiModel(value = "WarehouseShelf对象", description = "WMS仓库货架表")
public class WarehouseShelfSaveCommand implements Serializable {


    @ApiModelProperty("仓库ID")
    private Long warehouseId;

    @ApiModelProperty("货架编号")
    private String shelfCode;

    @ApiModelProperty("说明备注")
    private String shelfComment;


}
