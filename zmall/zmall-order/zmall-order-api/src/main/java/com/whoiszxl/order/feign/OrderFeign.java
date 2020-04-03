package com.whoiszxl.order.feign;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-31
 **/
@FeignClient(name = "zmall-order-web")
public interface OrderFeign {

    @PostMapping("/order")
    Result add(@RequestBody Order order);

    @GetMapping("/order/{id}")
    Result<Order> findById(@PathVariable("id") String id);
}
