package com.whoiszxl.taowu.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户地址feign DTO
 *
 * @author whoiszxl
 * @date 2022/4/15
 */
@Data
@Schema(description = "MemberAddressFeignDTO")
public class MemberAddressFeignDTO implements Serializable {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "收货人")
    private String receiverName;

    @Schema(description = "收货人电话号码")
    private String receiverPhone;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "收货地址")
    private String detailAddress;

    @Schema(description = "是否默认 1:默认 2:非默认")
    private Integer isDefault;

}
