package com.whoiszxl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.entity.member.MemberPO;

import java.util.Collection;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-01
 */
public interface MemberMapper extends BaseMapper<MemberPO> {


    /**
     * 批量插入（mysql）
     * @param entityList
     * @return
     */
    Integer insertBatchSomeColumn(Collection<MemberPO> entityList);
}
