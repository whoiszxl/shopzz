package com.whoiszxl.cqrs.cache;

import com.baomidou.mybatisplus.annotation.*;
import com.whoiszxl.entity.SeckillItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@ApiModel(value = "SeckillItem缓存对象", description = "秒杀item表")
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
