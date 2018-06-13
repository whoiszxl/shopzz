package com.whoiszxl.dao;

import java.util.List;

import com.whoiszxl.entity.Keywords;

public interface KeywordsMapper {
    int deleteByPrimaryKey(Byte id);

    int insert(Keywords record);

    int insertSelective(Keywords record);

    Keywords selectByPrimaryKey(Byte id);

    int updateByPrimaryKeySelective(Keywords record);

    int updateByPrimaryKey(Keywords record);

	List<Keywords> selectAllKeywords();
}