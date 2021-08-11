package com.whoiszxl.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详细信息
 *
 * @author whoiszxl
 * @date 2021/8/11
 */
@Data
public class OrderInfoDTO implements Serializable {

    private Long memberId;

    private Long orderId;

    private List<OrderItemDTO> orderItemDTOList;

}
