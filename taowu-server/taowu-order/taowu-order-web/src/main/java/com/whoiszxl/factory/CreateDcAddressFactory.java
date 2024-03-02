package com.whoiszxl.factory;

import com.whoiszxl.feign.BTCFeignClient;
import com.whoiszxl.feign.CreateAddressFeignClient;
import com.whoiszxl.feign.ETHFeignClient;
import com.whoiszxl.feign.Erc20FeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 数字货币地址创建工厂
 *
 * @author whoiszxl
 * @date 2021/8/3
 */
@Component
@RequiredArgsConstructor
public class CreateDcAddressFactory {

    private final BTCFeignClient btcFeignClient;

    private final ETHFeignClient ethFeignClient;

    private final Erc20FeignClient erc20FeignClient;

    /**
     * 通过数字货币名称获取地址创建客户端
     * @param dcName 数字货币名称
     * @return
     */
    public CreateAddressFeignClient get(String dcName) {
        dcName = dcName.toUpperCase().trim();
        switch (dcName) {
            case "BTC":
                return btcFeignClient;
            case "ETH":
                return ethFeignClient;
            case "SHOPZZ":
                return erc20FeignClient;
            default:
                return btcFeignClient;
        }
    }
}
