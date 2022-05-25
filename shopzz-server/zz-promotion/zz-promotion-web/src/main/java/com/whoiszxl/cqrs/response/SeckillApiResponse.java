package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 秒杀表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Data
@ApiModel(value = "Seckill 返回对象", description = "秒杀表")
public class SeckillApiResponse implements Serializable {

    private List<SeckillVO> seckillList;

    public static SeckillApiResponse build(List<SeckillVO> seckillList) {
        SeckillApiResponse seckillApiResponse = new SeckillApiResponse();
        seckillApiResponse.setSeckillList(seckillList);
        return seckillApiResponse;
    }
}
