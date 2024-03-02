package com.whoiszxl.taowu.im.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 好友批量添加结果返回实体
 * @author whoiszxl
 */
@Data
@AllArgsConstructor
@Schema(description = "好友批量添加结果返回实体")
public class FriendAddMultiResultResponse {

    @Schema(description = "失败的好友账号ID列表")
    private List<String> failAccounts;
    
}
