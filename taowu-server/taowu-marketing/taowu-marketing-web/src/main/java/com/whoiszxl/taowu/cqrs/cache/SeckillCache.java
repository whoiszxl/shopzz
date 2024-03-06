package com.whoiszxl.taowu.cqrs.cache;

import com.whoiszxl.taowu.entity.Seckill;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 秒杀活动缓存对象
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Data
@Accessors(chain = true)
@Schema(description = "秒杀活动缓存对象")
public class SeckillCache implements Serializable {

    protected boolean exist;

    private Seckill seckill;

    private Long version;

    private boolean later;

    public SeckillCache with(Seckill seckill) {
        this.exist = true;
        this.seckill = seckill;
        return this;
    }

    public SeckillCache withVersion(Long version) {
        this.version = version;
        return this;
    }

    public SeckillCache tryLater() {
        this.later = true;
        return this;
    }

    public SeckillCache notExist() {
        this.exist = false;
        return this;
    }

}
