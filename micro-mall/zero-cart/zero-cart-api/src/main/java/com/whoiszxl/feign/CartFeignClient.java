package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 库存中心 库存对外接口
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
@FeignClient(name = "zero-cart-web", contextId = "cartFeign", configuration = OAuth2FeignConfig.class)
public interface CartFeignClient {

    /**
     * 获取当前登录会员的选中的购物车列表
     * @return 选中的购物车列表
     */
    @PostMapping("/getCheckedCartItem")
    ResponseResult<List<CartDTO>> getCheckedCartItem();
}
