package com.whoiszxl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.dao.CategoryMapper;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.entity.Category;
import com.whoiszxl.service.CategoryService;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.vo.BannerVo;
import com.whoiszxl.vo.CategoryVo;

/**
 * 
 * @author whoiszxl
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
    private CategoryMapper categoryMapper;
	
	@Override
	public ServerResponse addCategory(String categoryName, String img, Integer parentId) {
		if(parentId == null || StringUtils.isBlank(categoryName)) {
			return ServerResponse.createByErrorMessage("添加品类参数错误");
		}
		
		Category category = new Category();
		category.setName(categoryName);
		category.setParentId(parentId);
		category.setImg(img);
		category.setStatus(true);
		
		int rowCount = categoryMapper.insert(category);
		if(rowCount > 0) {
			return ServerResponse.createBySuccessMessage("添加品类成功");
		}
		return ServerResponse.createByErrorMessage("添加品类失败");
	}

	@Override
	public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
		if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
	}

	@Override
	public ServerResponse<List<CategoryVo>> getChildrenParallelCategory(Integer categoryId) {
		List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        
        List<CategoryVo> categoryVoList = new ArrayList<CategoryVo>();
        for (Category category : categoryList) {
        	CategoryVo categoryVo = new CategoryVo();
			BeanUtils.copyProperties(category, categoryVo);
			categoryVo.setImgHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
			categoryVoList.add(categoryVo);
		}
        
        return ServerResponse.createBySuccess(categoryVoList);
	}

	@Override
	public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
		//新建一个set集合
		Set<Category> categorySet = Sets.newHashSet();
		//调用递归
        findChildCategory(categorySet,categoryId);

        System.out.println(categorySet);
        //新建一个list集合
        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
        	//遍历将set中元素的id存到list中
            for(Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
	}
	
	//递归算法,算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet ,Integer categoryId){
    	//通过分类id查询到这个分类
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
        	//不为空添加到set
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件      查找这个分类的子节点
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        //遍历子节点并且继续用子节点调用
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }

	@Override
	public ServerResponse<String> saveOrUpdateCategory(Category category) {
		if (category != null) {

			if (category.getId() != null) {
				category.setUpdateTime(new Date());
				int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
				if (rowCount > 0) {
					return ServerResponse.createBySuccessMessage("更新分类成功");
				} else {
					return ServerResponse.createByErrorMessage("更新分类失败");
				}

			} else {
				category.setCreateTime(new Date());
				int rowCount = categoryMapper.insert(category);
				if (rowCount > 0) {
					return ServerResponse.createBySuccessMessage("新增分类成功");
				} else {
					return ServerResponse.createByErrorMessage("新增分类失败");
				}
			}
		}
		return ServerResponse.createByErrorMessage("新增或更新分类参数不正确了");
	}

	@Override
	public ServerResponse<CategoryVo> manageCategoryDetail(Integer categoryId) {
		Category category = categoryMapper.selectByPrimaryKey(categoryId);
		if(category == null) {
			return ServerResponse.createByErrorMessage("分类不存在");
		}
		CategoryVo categoryVo = new CategoryVo();
		BeanUtils.copyProperties(category, categoryVo);
		categoryVo.setImgHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
		return ServerResponse.createBySuccess(categoryVo);
	}

	@Override
	public ServerResponse<List<HashMap<String, Object>>> getIndexPageCategorys(int categoryMainCount) {
		//获取最顶级的分类
		List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentIdAndCount(0, categoryMainCount);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("没有顶级分类");
        }
        
    	List<HashMap<String, Object>> result = Lists.newArrayList();
    	int index = 1;
        for (Category mainCategory : categoryList) {
        	List<Category> minorCategoryList = categoryMapper.selectCategoryChildrenByParentIdAndCount(mainCategory.getId(), 5);
        	HashMap<String, Object> item = Maps.newHashMap();
        	item.put("index", index++);
        	item.put("imgHost", PropertiesUtil.getProperty("ftp.server.http.prefix"));
        	item.put("mainCategoryName", mainCategory.getName());
        	item.put("minorCategoryList", minorCategoryList);
        	result.add(item);
		}
        
		return ServerResponse.createBySuccess(result);
	}

}

