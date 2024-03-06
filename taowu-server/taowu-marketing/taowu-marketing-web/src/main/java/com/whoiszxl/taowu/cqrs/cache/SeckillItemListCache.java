package com.whoiszxl.taowu.cqrs.cache;

import com.whoiszxl.taowu.entity.SeckillItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "秒杀item列表缓存")
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
