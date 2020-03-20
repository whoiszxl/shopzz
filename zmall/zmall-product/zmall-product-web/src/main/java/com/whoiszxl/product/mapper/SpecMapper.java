package com.whoiszxl.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.product.entity.MallSpec;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
@Repository
public interface SpecMapper extends BaseMapper<MallSpec> {


    @Select("SELECT name,options FROM mall_spec WHERE template_id IN ( SELECT template_id FROM mall_category WHERE NAME=#{categoryName}) order by seq")
    List<Map> findSpecListByCategoryName(@Param("categoryName") String categoryName);
}
