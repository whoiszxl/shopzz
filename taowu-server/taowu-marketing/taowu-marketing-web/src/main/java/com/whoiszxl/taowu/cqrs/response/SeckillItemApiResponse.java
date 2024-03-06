package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 秒杀item表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Data
@Schema(description = "秒杀item表")
public class SeckillItemApiResponse implements Serializable {

    private List<SeckillItemVO> seckillItemVOList;

    public static SeckillItemApiResponse build(List<SeckillItemVO> seckillItemVOList) {
        SeckillItemApiResponse seckillItemApiResponse = new SeckillItemApiResponse();
        seckillItemApiResponse.setSeckillItemVOList(seckillItemVOList);
        return seckillItemApiResponse;
    }

}
