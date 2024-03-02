package com.whoiszxl.taowu.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.im.cqrs.command.FriendAddCommand;
import com.whoiszxl.taowu.im.cqrs.command.FriendDeleteCommand;
import com.whoiszxl.taowu.im.cqrs.command.FriendRequestApproveCommand;
import com.whoiszxl.taowu.im.cqrs.query.FriendFetchOneQuery;
import com.whoiszxl.taowu.im.entity.Friend;
import com.whoiszxl.taowu.im.entity.FriendRequest;

import java.util.List;

/**
 * <p>
 * 好友表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
public interface IFriendService extends IService<Friend> {

    /**
     * 好友批量添加
     * @param command 批量添加命令
     * @return 是否添加成功
     */
    Boolean friendAdd(FriendAddCommand command);


    /**
     * 删除好友
     * @param command 删除好友命令
     * @return 是否删除成功
     */
    Boolean friendDelete(FriendDeleteCommand command);

    /**
     * 全量拉取好友列表
     * @return 好友列表
     */
    List<Friend> friendFetch();

    /**
     * 拉取指定好友
     * @param query 查询条件
     * @return 好友详细信息
     */
    Friend friendFetchOne(FriendFetchOneQuery query);
    
    /**
     * 保存好友关系
     * @param command 好友添加命令
     * @return 是否保存成功
     */
    boolean saveFriendRelation(FriendAddCommand command);

    /**
     * 新增或保存一条好友申请记录
     * @param command 好友新增的命令
     * @return 是否新增或保存成功
     */
    Boolean friendRequestSaveOrUpdate(FriendAddCommand command);

    /**
     * 好友请求审批
     * @param command 审批命令
     * @return 审批结果
     */
    Boolean friendRequestApprove(FriendRequestApproveCommand command);

    /**
     * 获取好友请求列表
     * @return 好友请求列表
     */
    List<FriendRequest> friendRequestList();

    /**
     * 校验双方是否为好友
     * @param fromMemberId 用户id
     * @param toMemberId 用户id
     * @return 是否为好友
     */
    boolean checkFriend(Long fromMemberId, Long toMemberId);
}
