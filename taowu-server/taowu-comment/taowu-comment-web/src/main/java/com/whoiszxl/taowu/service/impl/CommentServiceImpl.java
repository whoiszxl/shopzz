package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.entity.response.PageResponse;
import com.whoiszxl.taowu.common.enums.StatusEnum;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.token.entity.AppLoginMember;
import com.whoiszxl.taowu.common.utils.AssertUtils;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.common.utils.IpUtils;
import com.whoiszxl.taowu.cqrs.command.CommentDeleteCommand;
import com.whoiszxl.taowu.cqrs.command.CommentPublishCommand;
import com.whoiszxl.taowu.cqrs.command.CommentTopCommand;
import com.whoiszxl.taowu.cqrs.query.CommentQuery;
import com.whoiszxl.taowu.cqrs.response.CommentResponse;
import com.whoiszxl.taowu.dto.VideoAuditMqDto;
import com.whoiszxl.taowu.entity.Comment;
import com.whoiszxl.taowu.enums.CommentModuleEnum;
import com.whoiszxl.taowu.feign.SensitiveWordFeignClient;
import com.whoiszxl.taowu.feign.VideoFeignClient;
import com.whoiszxl.taowu.mapper.CommentMapper;
import com.whoiszxl.taowu.service.ICommentService;
import lombok.RequiredArgsConstructor;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 评论主表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2024-03-26
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    private final VideoFeignClient videoFeignClient;

    private final SensitiveWordFeignClient sensitiveWordFeignClient;

    private final TokenHelper tokenHelper;

    private final Ip2regionSearcher regionSearcher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishComment(CommentPublishCommand command) {
        AppLoginMember appLoginMember = tokenHelper.getAppLoginMember();
        // 1. 评论有效性校验
        // 1.1 判断 objId 是否存在，可以通过布隆过滤器或者 bitmap 的方式来判断，不然需要多一步 feign 调用
        AssertUtils.isTrue(StrUtil.isNotBlank(command.getContent()), "评论内容不能为空");
        AssertUtils.isTrue(CommentModuleEnum.isValid(command.getModule()), "Module 参数无效");
        ResponseResult<Boolean> result = videoFeignClient.checkVideoExistById(command.getObjId());
        AssertUtils.isTrue(result.getData(), "ObjId 参数无效");

        // 1.2 判断 parentId 是否存在，如果存在，则需要判断 replyId 是否也存在
        if(ObjUtil.isNotNull(command.getParentId()) && ObjUtil.notEqual(command.getParentId(), 0L)) {
            Comment rootComment = this.getById(command.getParentId());
            AssertUtils.isNotNull(rootComment, "ParentId 参数无效");

            if(ObjUtil.isNotNull(command.getReplyId()) && ObjUtil.notEqual(command.getReplyId(), 0L)) {
                Comment replyComment = this.getById(command.getReplyId());
                AssertUtils.isNotNull(replyComment, "ReplyId 参数无效");
            }
        }

        // 1.3 判断 content 是否存在敏感内容
        VideoAuditMqDto videoAuditMqDto = new VideoAuditMqDto();
        videoAuditMqDto.setCommentContent(command.getContent());
        videoAuditMqDto.setMemberId(appLoginMember.getId());
        videoAuditMqDto.setMemberAvatar(appLoginMember.getAvatar());
        videoAuditMqDto.setIp(appLoginMember.getIp());
        ResponseResult<Boolean> sensitiveResult = sensitiveWordFeignClient.verifyKeyword(videoAuditMqDto);
        AssertUtils.isTrue(sensitiveResult.isOk(), "内容审核不通过");

        // 2. 评论落库
        Comment comment = BeanUtil.copyProperties(command, Comment.class);
        comment.setAvatar(appLoginMember.getAvatar());
        comment.setUsername(appLoginMember.getFullName());
        comment.setMemberId(appLoginMember.getId());
        comment.setIpAddress(regionSearcher.memorySearch(command.getClientIp()).getCity());
        boolean saveFlag = this.save(comment);
        AssertUtils.isTrue(saveFlag, "评论发布失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(CommentDeleteCommand command) {
        // 校验评论是否存在
        AppLoginMember appLoginMember = tokenHelper.getAppLoginMember();
        Comment comment = this.getOne(Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getMemberId, appLoginMember.getId())
                .eq(Comment::getObjId, command.getObjId())
                .eq(Comment::getModule, command.getModule())
                .eq(Comment::getId, command.getCommentId()));
        AssertUtils.isNotNull(comment, "评论不存在");


        boolean flag = this.removeById(comment.getId());
        AssertUtils.isTrue(flag, "评论删除失败");

        // 判断是否是根评论，若是则需要删除根评论下的其他评论
        if(comment.getParentId() != 0L) {
            flag = this.remove(Wrappers.<Comment>lambdaQuery()
                    .eq(Comment::getObjId, command.getObjId())
                    .eq(Comment::getModule, command.getModule())
                    .eq(Comment::getParentId, command.getCommentId()));
            AssertUtils.isTrue(flag, "评论删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void topComment(CommentTopCommand command) {
        AppLoginMember appLoginMember = tokenHelper.getAppLoginMember();
        // 只能置顶自己发表的评论
        boolean updateFlag = this.update(Wrappers.<Comment>lambdaUpdate()
                .eq(Comment::getMemberId, appLoginMember.getId())
                .eq(Comment::getId, command.getCommentId())
                .set(Comment::getTopStatus, StatusEnum.OPEN.getCode()));
        AssertUtils.isTrue(updateFlag, "置顶评论失败");
    }

    @Override
    public PageResponse<CommentResponse> queryComment(CommentQuery query) {
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getParentId, query.getParentId())
                .eq(Comment::getModule, query.getModule())
                .eq(Comment::getObjId, query.getObjId());
        IPage<Comment> talkPage = this.page(query.toPage(), queryWrapper);
        return PageResponse.convert(talkPage, CommentResponse.class);
    }
}
