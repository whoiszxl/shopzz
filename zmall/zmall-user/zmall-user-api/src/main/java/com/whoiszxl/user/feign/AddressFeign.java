package com.whoiszxl.user.feign;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.user.entity.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-25
 **/
@FeignClient(name = "zmall-user-web")
public interface AddressFeign {

    @GetMapping("/address/list")
    Result<List<Address>> list();
}
