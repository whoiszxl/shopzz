package com.whoiszxl.taowu.common.entity;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 分页查询对象
 *
 * @author whoiszxl
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "分页查询对象")
public class ZSetPageQuery implements Serializable {

    @Schema(description = "ZSET 最大分数值")
    @Min(value = 1, message = "分数值最小值为 {value}")
    private Long maxScore;

    @Schema(description = "页码")
    private Integer page;

    @Schema(description = "每页数量")
    @Range(min = 5, max=200, message = "每页数量最小为{min},最大为{max}")
    private Integer size = 10;

}
