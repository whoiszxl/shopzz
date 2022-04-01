package com.whoiszxl.db.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis数据填充
 *
 * @author whoiszxl
 * @date 2021/7/12
 */
@Component
public class EntityMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long id = StpUtil.getLoginIdAsLong();
        this.setFieldValByName("createdBy", id.toString(), metaObject);
        this.setFieldValByName("updatedBy", id.toString(), metaObject);
        this.setFieldValByName("createdAt", new Date(), metaObject);
        this.setFieldValByName("updatedAt", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long id = StpUtil.getLoginIdAsLong();
        this.setFieldValByName("updatedAt", new Date(), metaObject);
        this.setFieldValByName("updatedBy", id.toString(), metaObject);
    }
}
