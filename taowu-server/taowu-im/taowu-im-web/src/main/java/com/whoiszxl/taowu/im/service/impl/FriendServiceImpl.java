package com.whoiszxl.taowu.im.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.im.constants.FriendRequestStatusEnum;
import com.whoiszxl.taowu.im.constants.FriendStatusEnum;
import com.whoiszxl.taowu.im.constants.FriendVerificationEnum;
import com.whoiszxl.taowu.im.cqrs.command.FriendAddCommand;
import com.whoiszxl.taowu.im.cqrs.command.FriendDeleteCommand;
import com.whoiszxl.taowu.im.cqrs.command.FriendRequestApproveCommand;
import com.whoiszxl.taowu.im.cqrs.query.FriendFetchOneQuery;
import com.whoiszxl.taowu.im.entity.Friend;
import com.whoiszxl.taowu.im.entity.FriendRequest;
import com.whoiszxl.taowu.im.mapper.FriendMapper;
import com.whoiszxl.taowu.im.service.IFriendRequestService;
import com.whoiszxl.taowu.im.service.IFriendService;
import com.whoiszxl.taowu.member.dto.MemberDTO;
import com.whoiszxl.taowu.member.feign.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 好友表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {

    private final IFriendRequestService friendRequestService;

    private final TokenHelper tokenHelper;
    
    private final MemberFeignClient memberFeignClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean friendAdd(FriendAddCommand command) {
        Long fromMemberId = tokenHelper.getAppMemberId();
        FriendAddCommand.AddFriendItem addFriendItem = command.getAddFriendItem();

        //1. 校验被添加好友是否存在，从member服务获取
        MemberDTO toMemberDTO = memberFeignClient.getMemberInfoById(addFriendItem.getToMemberId()).getData();
        Assert.isTrue(toMemberDTO != null, "被添加好友不存在");
        Integer friendVerificationStatus = 1;

        if(ObjUtil.isNotNull(friendVerificationStatus)
                && ObjUtil.equal(friendVerificationStatus, FriendVerificationEnum.NOT_NEED_VERIFICATION.getCode()) ) {
            //3. 好友无需验证，直接添加好友
            saveFriendRelation(command);
        }else {
            //4. 好友需要验证
            Friend friend = this.lambdaQuery()
                    .eq(Friend::getFromMemberId, fromMemberId)
                    .eq(Friend::getToMemberId, addFriendItem.getToMemberId()).one();
            if(friend == null || ObjUtil.equal(friend.getStatus(), FriendStatusEnum.DELETE.getCode())) {
                //5. 好友关系不存在，或者好友关系为删除状态，则新增
                Boolean flag = friendRequestSaveOrUpdate(command);
                Assert.isTrue(flag, "新增好友失败");
            }else if(ObjUtil.equal(friend.getStatus(), FriendStatusEnum.VALID.getCode())) {
                ExceptionCatcher.catchServiceEx("已经是好友了");
            }
        }

        return true;
    }

    @Override
    public boolean saveFriendRelation(FriendAddCommand command) {
        //获取好友关系
        FriendAddCommand.AddFriendItem addFriendItem = command.getAddFriendItem();
        Friend friend = this.lambdaQuery()
                .eq(Friend::getFromMemberId, command.getFromMemberId())
                .eq(Friend::getToMemberId, addFriendItem.getToMemberId()).one();

        //判断好友关系是否存在，不存在新增，存在则更新
        if(friend == null) {
            //新增好友关系
            Friend saveFriend = BeanUtil.copyProperties(addFriendItem, Friend.class);
            saveFriend.setFromMemberId(command.getFromMemberId());
            saveFriend.setStatus(FriendStatusEnum.VALID.getCode());
            this.save(saveFriend);

            //新增反向的好友关系
            saveFriend.setFromMemberId(saveFriend.getToMemberId());
            saveFriend.setToMemberId(command.getFromMemberId());
            return this.save(saveFriend);
        }else {
            if(ObjUtil.equal(friend.getStatus(), FriendStatusEnum.VALID.getCode())) {
                ExceptionCatcher.catchServiceEx("已经添加了此好友");
            }

            // 更新好友关系
            LambdaUpdateChainWrapper<Friend> friendLambdaUpdateChainWrapper = this.lambdaUpdate();
            friendLambdaUpdateChainWrapper.set(Friend::getStatus, FriendStatusEnum.VALID.getCode());

            //更新添加来源，备注以及扩展信息
            if(StrUtil.isNotBlank(addFriendItem.getSource())) {
                friendLambdaUpdateChainWrapper.set(Friend::getSource, addFriendItem.getSource());
            }
            if(StrUtil.isNotBlank(addFriendItem.getRemark())) {
                friendLambdaUpdateChainWrapper.set(Friend::getRemark, addFriendItem.getRemark());
            }
            if(StrUtil.isNotBlank(addFriendItem.getExtra())) {
                friendLambdaUpdateChainWrapper.set(Friend::getExtra, addFriendItem.getExtra());
            }

            friendLambdaUpdateChainWrapper
                    .eq(Friend::getFromMemberId, friend.getFromMemberId())
                    .eq(Friend::getToMemberId, friend.getToMemberId());

            boolean updateFlag = friendLambdaUpdateChainWrapper.update();
            Assert.isTrue(updateFlag, "好友添加失败");

            //反向更新好友信息
            friendLambdaUpdateChainWrapper
                    .eq(Friend::getFromMemberId, friend.getToMemberId())
                    .eq(Friend::getToMemberId, friend.getFromMemberId());
            updateFlag = friendLambdaUpdateChainWrapper.update();
            Assert.isTrue(updateFlag, "好友添加失败");

        }
        return true;
    }

    @Override
    public Boolean friendDelete(FriendDeleteCommand command) {
        Long fromMemberId = tokenHelper.getAppMemberId();
        Friend friend = this.lambdaQuery()
                .eq(Friend::getFromMemberId, fromMemberId)
                .eq(Friend::getToMemberId, command.getToMemberId()).one();

        if(friend ==null) {
            ExceptionCatcher.catchServiceEx("对方不是你的好友");
        }

        if(ObjUtil.notEqual(friend.getStatus(), FriendStatusEnum.DELETE.getCode()) ) {
            this.lambdaUpdate()
                    .set(Friend::getStatus, FriendStatusEnum.DELETE.getCode())
                    .eq(Friend::getFromMemberId, fromMemberId)
                    .eq(Friend::getToMemberId, command.getToMemberId()).update();

            //TODO pack发送
        }else {
            ExceptionCatcher.catchServiceEx("好友已被删除");
        }

        return true;
    }

    @Override
    public List<Friend> friendFetch() {
        Long fromMemberId = tokenHelper.getAppMemberId();
        return this.lambdaQuery()
                .eq(Friend::getFromMemberId, fromMemberId)
                .eq(Friend::getStatus, FriendStatusEnum.VALID.getCode()).list();
    }

    @Override
    public Friend friendFetchOne(FriendFetchOneQuery query) {
        Long fromMemberId = tokenHelper.getAppMemberId();
        return this.lambdaQuery()
                .eq(Friend::getFromMemberId, fromMemberId)
                .eq(Friend::getToMemberId, query.getToMemberId())
                .eq(Friend::getStatus, FriendStatusEnum.VALID.getCode()).one();
    }
    

    @Override
    public Boolean friendRequestSaveOrUpdate(FriendAddCommand command) {
        Long fromMemberId = tokenHelper.getAppMemberId();
        FriendAddCommand.AddFriendItem addFriendItem = command.getAddFriendItem();

        FriendRequest friendRequest = friendRequestService.lambdaQuery()
                .eq(FriendRequest::getFromMemberId, fromMemberId)
                .eq(FriendRequest::getToMemberId, addFriendItem.getToMemberId()).one();

        if(friendRequest == null) {
            //新增逻辑
            friendRequest = new FriendRequest();
            friendRequest.setId(IdUtil.getSnowflakeNextId());
            friendRequest.setFromMemberId(fromMemberId);
            friendRequest.setToMemberId(addFriendItem.getToMemberId());
            friendRequest.setRemark(addFriendItem.getRemark());
            friendRequest.setSource(addFriendItem.getSource());
            friendRequest.setFriendVerification(addFriendItem.getAddWording());
            friendRequest.setStatus(FriendRequestStatusEnum.NOT_AUDIT.getCode());
            return friendRequestService.save(friendRequest);
        }else {
            //更新逻辑
            if(StrUtil.isNotBlank(addFriendItem.getAddWording())) {
                friendRequest.setFriendVerification(addFriendItem.getAddWording());
            }
            if(StrUtil.isNotBlank(addFriendItem.getSource())) {
                friendRequest.setSource(addFriendItem.getSource());
            }
            if(StrUtil.isNotBlank(addFriendItem.getRemark())) {
                friendRequest.setRemark(addFriendItem.getRemark());
            }
            friendRequest.setStatus(FriendRequestStatusEnum.NOT_AUDIT.getCode());
            return friendRequestService.updateById(friendRequest);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean friendRequestApprove(FriendRequestApproveCommand command) {
        Long id = command.getId();
        Integer status = command.getStatus();
        Long approveMemberId = tokenHelper.getAppMemberId();

        FriendRequest friendRequest = friendRequestService.getById(id);
        Assert.isTrue(friendRequest != null, "好友申请记录不存在");
        assert friendRequest != null;
        Assert.isTrue(ObjUtil.equal(friendRequest.getToMemberId(), approveMemberId),
                "只能审批发送给自己账号的好友请求");

        boolean updateFlag = friendRequestService.lambdaUpdate()
                .set(FriendRequest::getStatus, status)
                .eq(FriendRequest::getId, id)
                .update();
        Assert.isTrue(updateFlag, "好友审批失败");

        //如果同意了好友申请，则形成好友关系
        if(ObjUtil.equal(FriendRequestStatusEnum.AUDIT_SUCCESS.getCode(), status)) {
            FriendAddCommand friendAddCommand = BeanUtil.copyProperties(command, FriendAddCommand.class);
            friendAddCommand.setFromMemberId(friendRequest.getFromMemberId());

            FriendAddCommand.AddFriendItem addFriendItem = new FriendAddCommand.AddFriendItem();
            addFriendItem.setToMemberId(friendRequest.getToMemberId());
            addFriendItem.setAddWording(friendRequest.getFriendVerification());
            addFriendItem.setRemark(friendRequest.getRemark());
            addFriendItem.setSource(friendRequest.getSource());
            friendAddCommand.setAddFriendItem(addFriendItem);
            boolean saveFlag = saveFriendRelation(friendAddCommand);
            Assert.isTrue(saveFlag, "好友审批失败");
        }

        return true;
    }

    @Override
    public List<FriendRequest> friendRequestList() {
        Long memberId = tokenHelper.getAppMemberId();
        return friendRequestService.lambdaQuery()
                .eq(FriendRequest::getToMemberId, memberId).list();
    }

    @Override
    public boolean checkFriend(Long fromMemberId, Long toMemberId) {
        long count = this.count(Wrappers.<Friend>lambdaQuery()
                .eq(Friend::getFromMemberId, fromMemberId)
                .eq(Friend::getToMemberId, toMemberId));
        return count > 0;
    }

}
