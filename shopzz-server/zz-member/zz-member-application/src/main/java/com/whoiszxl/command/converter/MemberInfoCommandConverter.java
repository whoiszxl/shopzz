package com.whoiszxl.command.converter;

import com.whoiszxl.aggregate.model.MemberInfo;
import com.whoiszxl.command.cmd.UpdateMemberCommand;
import com.whoiszxl.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户详情命令转换器
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Component
public class MemberInfoCommandConverter {

    @Autowired
    private DozerUtils dozerUtils;

    public MemberInfo commandToDomain(UpdateMemberCommand updateMemberCommand) {
        MemberInfo memberInfo = dozerUtils.map(updateMemberCommand, MemberInfo.class);
        return memberInfo;
    }

    public UpdateMemberCommand domainToCommand(MemberInfo memberInfo) {
        UpdateMemberCommand updateMemberCommand = dozerUtils.map(memberInfo, UpdateMemberCommand.class);
        return updateMemberCommand;
    }
}
