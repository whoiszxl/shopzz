package com.whoiszxl.taowu.cqrs.response;

import com.whoiszxl.taowu.cqrs.vo.CouponApiVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Coupon api返回实体")
public class CouponApiResponse implements Serializable {

    @Schema(description = "自增主键ID")
    private List<CouponApiVO> couponList;

}
