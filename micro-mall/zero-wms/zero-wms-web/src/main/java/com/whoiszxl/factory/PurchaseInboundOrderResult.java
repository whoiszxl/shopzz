package com.whoiszxl.factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseInboundOrderResult {

    /** 处理结果 */
    private Boolean result;

    /** 是否执行下一个handler */
    private Boolean doNext = true;

    PurchaseInboundOrderResult(Boolean result) {
        this.result = result;
    }
}
