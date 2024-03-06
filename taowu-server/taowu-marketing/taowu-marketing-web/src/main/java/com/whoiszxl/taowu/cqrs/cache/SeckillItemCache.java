package com.whoiszxl.taowu.cqrs.cache;

import com.whoiszxl.taowu.entity.SeckillItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * SeckillItem缓存对象
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Data
@Accessors(chain = true)
@Schema(description = "秒杀item表")
public class SeckillItemCache implements Serializable {

    protected boolean exist;

    private SeckillItem seckillItem;

    private Long version;

    private boolean later;

    public SeckillItemCache with(SeckillItem seckillItem) {
        this.exist = true;
        this.seckillItem = seckillItem;
        return this;
    }

    public SeckillItemCache withVersion(Long version) {
        this.version = version;
        return this;
    }

    public SeckillItemCache tryLater() {
        this.later = true;
        return this;
    }

    public SeckillItemCache notExist() {
        this.exist = false;
        return this;
    }

}
