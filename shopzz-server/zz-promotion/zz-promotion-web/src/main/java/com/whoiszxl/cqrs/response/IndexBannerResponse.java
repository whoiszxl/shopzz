package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "主页banner聚合VO")
public class IndexBannerResponse {

    @ApiModelProperty(value = "主页Banner")
    private List<HomeBannerVO> banners;

    @ApiModelProperty(value = "主页navigation")
    private List<HomeBannerVO> navigations;

}
