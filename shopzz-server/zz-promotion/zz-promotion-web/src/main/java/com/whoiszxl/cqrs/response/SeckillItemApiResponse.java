package com.whoiszxl.cqrs.response;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 秒杀item表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Getter
@Setter
@TableName("spms_seckill_item")
@ApiModel(value = "SeckillItem对象", description = "秒杀item表")
public class SeckillItemApiResponse implements Serializable {

    private List<SeckillItemVO> seckillItemVOList;

    public static SeckillItemApiResponse build(List<SeckillItemVO> seckillItemVOList) {
        SeckillItemApiResponse seckillItemApiResponse = new SeckillItemApiResponse();
        seckillItemApiResponse.setSeckillItemVOList(seckillItemVOList);
        return seckillItemApiResponse;
    }

}
