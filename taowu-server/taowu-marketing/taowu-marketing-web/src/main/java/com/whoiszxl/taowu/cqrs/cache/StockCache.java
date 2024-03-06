package com.whoiszxl.taowu.cqrs.cache;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StockCache {
    protected boolean exist;
    private Integer availableStockQuantity;
    private boolean success;
    private boolean later;

    public StockCache with(Integer availableStockQuantity) {
        this.exist = true;
        this.availableStockQuantity = availableStockQuantity;
        this.success = true;
        return this;
    }

    public StockCache tryLater() {
        this.later = true;
        this.success = false;
        return this;
    }

    public StockCache notExist() {
        this.exist = false;
        this.success = true;
        return this;
    }
}
