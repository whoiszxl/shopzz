package com.whoiszxl.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.whoiszxl.query.model.qry.MemberQuery;
import com.whoiszxl.query.model.response.MemberDetailResponse;
import com.whoiszxl.query.model.response.MemberResponse;

/**
 * 用户查询应用服务接口
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
public interface MemberQueryApplicationService {

    /**
     * 查询当前登录用户的用户信息
     * @return
     */
    MemberResponse memberInfo();

    /**
     * 通过用户主键ID获取用户详细信息
     * @param memberId
     * @return
     */
    MemberDetailResponse memberDetailById(String memberId);

    /**
     * 管理后台查询用户列表
     * @param query
     * @return
     */
    IPage<MemberResponse> list(MemberQuery query);

}
