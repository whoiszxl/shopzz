package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="仓储管理员收供应商的货的接口的VO", description="仓储管理员收供应商的货的接口的VO")
public class ReceiveProductCommand {
    @ApiModelProperty(value = "到货时间")
    private LocalDateTime arrivalTime;

    @ApiModelProperty(value = "接收货物item条目集合")
    private List<ReceiveItem> receiveItems;

    @Data
    @ApiModel(value = "接收货物item条目")
    public static class ReceiveItem {

        @ApiModelProperty(value = "主键")
        private Long id;

        @ApiModelProperty(value = "合格的商品数量")
        private Integer qualifiedCount;

        @ApiModelProperty(value = "到货的商品数量")
        private Integer arrivalCount;
    }
}
