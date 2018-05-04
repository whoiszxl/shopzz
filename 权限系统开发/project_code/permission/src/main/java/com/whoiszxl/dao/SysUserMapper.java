package com.whoiszxl.dao;


import org.apache.ibatis.annotations.Param;

import com.whoiszxl.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

	SysUser findByKeyword(@Param("keyword") String keyword);

	int checkTelephoneExist(@Param("phone") String phone, @Param("userId") Integer userId);

	int checkEmailExist(@Param("mail") String mail, @Param("userId") Integer userId);
}