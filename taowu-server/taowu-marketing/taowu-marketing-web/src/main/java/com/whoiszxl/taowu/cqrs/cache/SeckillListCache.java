package com.whoiszxl.taowu.cqrs.cache;

import com.whoiszxl.taowu.entity.Seckill;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SeckillListCache {
    protected boolean exist;
    private List<Seckill> seckillList;
    private Long version;
    private boolean later;
    private Integer total;

    public SeckillListCache with(List<Seckill> seckillList) {
        this.exist = true;
        this.seckillList = seckillList;
        return this;
    }


    public SeckillListCache withVersion(Long version) {
        this.version = version;
        return this;
    }

    public SeckillListCache tryLater() {
        this.later = true;
        return this;
    }

    public SeckillListCache notExist() {
        this.exist = false;
        return this;
    }
}
