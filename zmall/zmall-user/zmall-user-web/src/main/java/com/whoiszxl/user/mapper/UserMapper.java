package com.whoiszxl.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Update("update mall_user set points=points+#{point} where username=#{username}")
    int updateUserPoint(@Param("username")String username, @Param("point") int point);
}
