package com.whoiszxl.taowu.common.entity.response;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义分页返回实体
 * @author whoiszxl
 */
@Data
@Schema(description = "SET集合的自定义分页")
public class SetPageResponse<T> {

    @Schema(description = "分页列表数据")
    private List<T> list;

    @Schema(description = "总记录数")
    private Long total;

    @Schema(description = "下一次查询的page页码")
    private Integer nextPage;

    @Schema(description = "下一次查询的最大分数")
    private Long nextMaxScore;

}
