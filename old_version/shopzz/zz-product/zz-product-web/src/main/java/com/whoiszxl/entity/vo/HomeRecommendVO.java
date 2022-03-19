package com.whoiszxl.entity.vo;

import com.whoiszxl.entity.Recommend;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author WHOISZXL
 * @date 2021/12/15
 */
@Data
public class HomeRecommendVO {

    private List<RecommendVO> hotRecommendList;

    private List<RecommendVO> niceRecommendList;
}
