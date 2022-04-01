package com.whoiszxl.command.impl;

import com.whoiszxl.aggregate.model.Member;
import com.whoiszxl.aggregate.model.MemberInfo;
import com.whoiszxl.aggregate.repository.MemberInfoRepository;
import com.whoiszxl.aggregate.repository.MemberRepository;
import com.whoiszxl.command.MemberApplicationService;
import com.whoiszxl.command.cmd.MemberLoginCommand;
import com.whoiszxl.command.cmd.UpdateMemberCommand;
import com.whoiszxl.command.converter.MemberCommandConverter;
import com.whoiszxl.command.converter.MemberInfoCommandConverter;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.events.MemberDomainEventPublisher;
import com.whoiszxl.utils.AssertUtils;
import com.whoiszxl.utils.AuthUtils;
import com.whoiszxl.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 会员应用服务实现
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Slf4j
@Service
public class MemberApplicationServiceImpl implements MemberApplicationService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberInfoRepository memberInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberDomainEventPublisher memberDomainEventPublisher;

    @Autowired
    private MemberCommandConverter memberCommandConverter;

    @Autowired
    private MemberInfoCommandConverter memberInfoCommandConverter;

    @Autowired
    private IdWorker idWorker;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMemberInfo(UpdateMemberCommand updateMemberCommand) {
        Long memberId = AuthUtils.getMemberId();

        //校验用户是否存在
        Member member = memberRepository.byId(memberId);
        AssertUtils.isTrue(Objects.nonNull(member), "用户不存在");

        //如果修改用户名，校验用户名是否已经存在了
        Member existMember = memberRepository.byUsername(updateMemberCommand.getUsername());
        AssertUtils.isTrue(Objects.isNull(existMember), "用户名已存在");

        //将请求命令转换为领域模型，再通过仓储服务进行更新
        Member updateMember = memberCommandConverter.commandToDomain(updateMemberCommand);
        MemberInfo updateMemberInfo = memberInfoCommandConverter.commandToDomain(updateMemberCommand);

        updateMember.bindMemberId(memberId);
        memberRepository.save(updateMember);

        updateMemberInfo.bindMemberId(memberId);
        memberInfoRepository.save(updateMemberInfo);

        //发布用户修改的领域事件
    }

    @Override
    public String login(MemberLoginCommand loginCommand) {
        //1. 校验账号密码是否匹配
        Member member = memberRepository.byUsername(loginCommand.getUsername());
        AssertUtils.isTrue(Objects.nonNull(member), "用户不存在");
        AssertUtils.isTrue(passwordEncoder.matches(loginCommand.getPassword(), member.getPassword()), "账号或密码错误");

        //2. 登录
        member.login();

        //3. 更新最后的登录时间
        //member.updateLastLoginTime();

        //4. 发布登录成功的领域事件，处理后续的活跃度更新，登录时间更新等操作
        //memberDomainEventPublisher.publishMemberLoginSuccessEvent(new MemberLoginSuccessEvent(member.getId()));

        //5. 返回token
        return member.getCurrentToken();
    }

    @Override
    @Transactional
    public void register(MemberLoginCommand loginCommand) {
        Member member = memberRepository.byUsername(loginCommand.getUsername());
        AssertUtils.isTrue(Objects.isNull(member), "用户已存在");

        String password = passwordEncoder.encode(loginCommand.getPassword());

        Member registerMember = new Member();
        registerMember.setId(idWorker.nextId());
        registerMember.setUsername(loginCommand.getUsername());
        registerMember.setPassword(password);
        registerMember.setPhone(loginCommand.getUsername());
        Member save = memberRepository.save(registerMember);

        Long memberId = save.getId();
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberId(memberId);
        memberInfoRepository.save(memberInfo);
    }

    @Override
    public void ban(Long memberId) {
        Member member = memberRepository.byId(memberId);
        AssertUtils.isTrue(Objects.nonNull(member), "用户不存在");

        member.setStatus(StatusEnum.CLOSE.getCode());
        memberRepository.update(member);
    }
}
