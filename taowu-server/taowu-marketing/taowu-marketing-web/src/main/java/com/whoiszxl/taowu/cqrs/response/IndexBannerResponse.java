package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "主页banner聚合VO")
public class IndexBannerResponse {

    @Schema(description = "主页Banner")
    private List<HomeBannerVO> banners;
}
