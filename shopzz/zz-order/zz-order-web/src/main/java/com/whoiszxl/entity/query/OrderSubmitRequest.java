package com.whoiszxl.entity.query;

import lombok.Data;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/1/12
 */
@Data
public class OrderSubmitRequest {

    private Long addressId;

    private String orderToken;

    private String usePoints;

    private String orderComment;

    private String couponId;
}
