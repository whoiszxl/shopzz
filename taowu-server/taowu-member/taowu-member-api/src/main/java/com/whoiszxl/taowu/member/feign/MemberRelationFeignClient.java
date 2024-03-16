package com.whoiszxl.taowu.member.feign;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.feign.FeignTokenConfig;
import com.whoiszxl.taowu.member.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 会员feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "taowu-member", contextId = "memberRelationFeign", configuration = FeignTokenConfig.class)
public interface MemberRelationFeignClient {

    /**
     * 获取用户的粉丝ID集合
     * @param memberId
     * @return
     */
    @GetMapping("/getMemberFollowerIdList")
    ResponseResult<List<String>> getMemberFollowerIdList(@RequestParam Long memberId);
}
