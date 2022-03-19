package com.whoiszxl.zero.feign;

import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.config.feign.OAuth2FeignConfig;
import com.whoiszxl.zero.dto.CartDTO;
import com.whoiszxl.zero.dto.CartListParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "zero-cart-web", contextId = "cartFeign", configuration = OAuth2FeignConfig.class, path = "/cart")
public interface CartFeign {

    @PostMapping("/cart/list")
    Result<List<CartDTO>> cartCheckedList(@RequestBody CartListParam cartListParam);
}
