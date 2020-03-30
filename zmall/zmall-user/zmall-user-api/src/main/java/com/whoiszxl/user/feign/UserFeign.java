package com.whoiszxl.user.feign;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-25
 **/
@FeignClient(name = "zmall-user-web")
public interface UserFeign {

    @GetMapping("/user/load/{username}")
    User findUserInfo(@PathVariable("username") String username);

    @GetMapping("/user/points/add/{username}")
    Result addPoints(@PathVariable("username") String username);
}