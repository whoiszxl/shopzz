package com.whoiszxl.taowu.im.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.whoiszxl.taowu.im.constants.TalkTypeEnum;
import com.whoiszxl.taowu.im.cqrs.command.TalkAddCommand;
import com.whoiszxl.taowu.im.cqrs.command.TalkDeleteCommand;
import com.whoiszxl.taowu.im.cqrs.query.TalkQuery;
import com.whoiszxl.taowu.im.cqrs.response.TalkResponse;
import com.whoiszxl.taowu.im.entity.Talk;
import com.whoiszxl.taowu.im.mapper.TalkMapper;
import com.whoiszxl.taowu.im.pack.PrivateChatReadPack;
import com.whoiszxl.taowu.im.service.ITalkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.zhipin.tools.common.entity.response.PageResponse;
import com.whoiszxl.zhipin.tools.common.token.TokenHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 对话表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-18
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk> implements ITalkService {

    private final TokenHelper tokenHelper;

    @Override
    public void read(PrivateChatReadPack privateChatReadPack) {
        Talk talk = this.getById(privateChatReadPack.getTalkId());
        if(talk == null) {
            talk = new Talk();
            talk.setId(IdUtil.getSnowflakeNextId());
            talk.setFromMemberId(privateChatReadPack.getFromMemberId());
            talk.setToMemberId(privateChatReadPack.getToMemberId());
            talk.setTalkType(privateChatReadPack.getTalkType());
            talk.setReadSequence(privateChatReadPack.getSequence());
            this.save(talk);
            return;
        }

        talk.setReadSequence(privateChatReadPack.getSequence());
        LambdaUpdateChainWrapper<Talk> wrapper = this.lambdaUpdate()
                .eq(Talk::getId, talk.getId())
                .lt(Talk::getReadSequence, privateChatReadPack.getSequence());
        this.update(talk, wrapper);
    }


    @Override
    public Boolean add(TalkAddCommand command) {
        //当用户对头头发起聊天请求的时候，便创建两个对话框，一个是用户的，一个是头头的
        Long fromMemberId = command.getFromMemberId();
        Long toMemberId = command.getToMemberId();
        //校验用户是否存在对话框，不存在则创建
        boolean existFlag = checkTalkBoxExist(fromMemberId, toMemberId);
        if(!existFlag) {
            Talk talk = new Talk();
            talk.setId(IdUtil.getSnowflakeNextId());
            talk.setFromMemberId(fromMemberId);
            talk.setToMemberId(toMemberId);
            talk.setTalkType(command.getTalkType());
            this.save(talk);
        }

        //校验头头是否存在对话框，不存在则创建
        existFlag = checkTalkBoxExist(toMemberId, fromMemberId);
        if(!existFlag) {
            Talk talk = new Talk();
            talk.setId(IdUtil.getSnowflakeNextId());
            talk.setFromMemberId(toMemberId);
            talk.setToMemberId(fromMemberId);
            talk.setTalkType(command.getTalkType());
            this.save(talk);
        }
        return true;
    }

    @Override
    public Boolean delete(TalkDeleteCommand command) {
        Long memberId = tokenHelper.getAppMemberId();
        return this.remove(Wrappers.<Talk>lambdaQuery()
                .eq(Talk::getId, command.getTalkId())
                .eq(Talk::getFromMemberId, memberId));
    }

    @Override
    public PageResponse<TalkResponse> talkList(TalkQuery talkQuery) {
        LambdaQueryWrapper<Talk> queryWrapper = Wrappers.<Talk>lambdaQuery()
                .eq(Talk::getToMemberId, tokenHelper.getAppMemberId());
        IPage<Talk> talkPage = this.page(talkQuery.toPage(), queryWrapper);
        PageResponse<TalkResponse> pageResponse = PageResponse.convert(talkPage, TalkResponse.class);

        //添加一个ChatGPT助手返回

        pageResponse.getList().add(0, buildGptTalk());

        return pageResponse;


    }

    private TalkResponse buildGptTalk() {
        return TalkResponse.builder()
                .talkType(TalkTypeEnum.GPT_CHAT.getCode())
                .fromMemberInfo("{\"name\": \"TT智能问答\", \"avatar\": \"https://shopzz.oss-cn-guangzhou.aliyuncs.com/other/a1.jpg\", \"jobTitle\": \"机器人\", \"companyAbbrName\": \"TT直聘\"}")
                .fromMemberId(-1L)
                .build();
    }

    private boolean checkTalkBoxExist(Long memberId, Long toMemberId) {
        Talk talk = this.getOne(Wrappers.<Talk>lambdaQuery()
                .eq(Talk::getFromMemberId, memberId)
                .eq(Talk::getToMemberId, toMemberId));
        return talk != null;
    }


}
