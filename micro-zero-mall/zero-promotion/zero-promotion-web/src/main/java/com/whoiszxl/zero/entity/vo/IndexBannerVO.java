package com.whoiszxl.zero.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 主页banner聚合VO
 *
 * @author whoiszxl
 * @date 2021/4/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "主页banner聚合VO")
public class IndexBannerVO {

    @ApiModelProperty(value = "主页Banner")
    private List<HomeBannerVO> banners;

    @ApiModelProperty(value = "主页navigation")
    private List<HomeBannerVO> navigations;

}
