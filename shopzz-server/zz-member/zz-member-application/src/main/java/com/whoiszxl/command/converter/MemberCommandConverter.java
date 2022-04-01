package com.whoiszxl.command.converter;

import com.whoiszxl.aggregate.model.Member;
import com.whoiszxl.command.cmd.UpdateMemberCommand;
import com.whoiszxl.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户命令转换器
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Component
public class MemberCommandConverter {

    @Autowired
    private DozerUtils dozerUtils;


    public Member commandToDomain(UpdateMemberCommand updateMemberCommand) {
        Member member = dozerUtils.map(updateMemberCommand, Member.class);
        return member;
    }

    public UpdateMemberCommand domainToCommand(Member member) {
        UpdateMemberCommand updateMemberCommand = dozerUtils.map(member, UpdateMemberCommand.class);
        return updateMemberCommand;
    }
}
