package com.whoiszxl.cqrs.response;

import com.whoiszxl.cqrs.vo.CouponApiVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "Coupon api返回实体", description = "Coupon api返回实体")
public class CouponApiResponse implements Serializable {

    @ApiModelProperty("自增主键ID")
    private List<CouponApiVO> couponList;

}
