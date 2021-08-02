package com.whoiszxl.dto;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="查询会员详情接口返回的VO实体", description="查询会员详情接口返回的VO实体")
public class MemberDetailDTO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("会员基础信息")
    private MemberDTO member;

    @ApiModelProperty("会员扩展信息")
    private MemberInfoDTO memberInfo;

}
