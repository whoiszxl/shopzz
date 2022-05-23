package com.whoiszxl.cqrs.cache;

import com.whoiszxl.entity.SeckillItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SeckillItemListCache {
    protected boolean exist;
    private List<SeckillItem> seckillItemList;
    private Long version;
    private boolean later;
    private Integer total;

    public SeckillItemListCache with(List<SeckillItem> seckillItemList) {
        this.exist = true;
        this.seckillItemList = seckillItemList;
        return this;
    }


    public SeckillItemListCache withVersion(Long version) {
        this.version = version;
        return this;
    }

    public SeckillItemListCache tryLater() {
        this.later = true;
        return this;
    }

    public SeckillItemListCache notExist() {
        this.exist = false;
        return this;
    }
}
