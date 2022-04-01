package com.whoiszxl.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录成功事件
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginSuccessEvent {

    private Long memberId;

}
