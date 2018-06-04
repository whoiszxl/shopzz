package com.whoiszxl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.whoiszxl.entity.Banner;

public interface BannerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);
    
    List<Banner> selectBannersByNum(@Param("num")int num);
}