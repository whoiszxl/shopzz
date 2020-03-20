package com.whoiszxl.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.product.entity.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
@Repository
public interface BrandMapper extends BaseMapper<Brand> {


    @Select("SELECT name,image FROM mall_brand WHERE id IN (SELECT brand_id FROM mall_category_brand WHERE category_id IN (SELECT id FROM mall_category WHERE NAME=#{name}) )order by seq")
    public List<Map> findBrandListByCategoryName(@Param("name") String categoryName);

}
