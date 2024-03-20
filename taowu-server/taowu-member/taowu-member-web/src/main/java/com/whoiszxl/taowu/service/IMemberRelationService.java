package com.whoiszxl.taowu.service;

/**
 * 用户关系服务
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
public interface IMemberRelationService {

    /**
     * 关注一个用户
     * @param memberId 用户id
     * @return 是否关注成功
     */
    Boolean attention(Long memberId);

    /**
     * 取消关注一个用户
     * @param memberId 用户id
     * @return 是否取消关注成功
     */
    Boolean unattention(Long memberId);
}
