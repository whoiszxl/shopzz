package com.whoiszxl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.entity.Server;

import java.util.Collection;

/**
 * <p>
 * 服务器表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-12
 */
public interface ServerMapper extends BaseMapper<Server> {

    /**
     * 批量插入（mysql）
     * @param entityList
     * @return
     */
    Integer insertBatchSomeColumn(Collection<Server> entityList);
}
