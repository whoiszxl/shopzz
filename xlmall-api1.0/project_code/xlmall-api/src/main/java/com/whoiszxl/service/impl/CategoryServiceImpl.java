package com.whoiszxl.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.dao.CategoryMapper;
import com.whoiszxl.entity.Category;
import com.whoiszxl.service.CategoryService;

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
	public ServerResponse addCategory(String categoryName, Integer parentId) {
		if(parentId == null || StringUtils.isBlank(categoryName)) {
			return ServerResponse.createByErrorMessage("添加品类参数错误");
		}
		
		Category category = new Category();
		category.setName(categoryName);
		category.setParentId(parentId);
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
	public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
		List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
	}

	@Override
	public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
		//新建一个set集合
		Set<Category> categorySet = Sets.newHashSet();
		//调用递归
        findChildCategory(categorySet,categoryId);

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

}

