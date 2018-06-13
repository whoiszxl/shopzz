package com.whoiszxl.service;

import java.util.List;

import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.entity.Keywords;
import com.whoiszxl.vo.BannerVo;

/**
 * 一些无需权限验证的文章，轮播图等之类的接口
 * @author whoiszxl
 *
 */
public interface ArticleService {
	
	
	/**
	 * 获取首页的轮播图
	 * @param num 获取轮播图的个数
	 * @return 轮播图列表
	 */
	List<BannerVo> getBannerList(int num);
	
	
	ServerResponse<List<Banner>> getBannerManageList(int num);


	ServerResponse<String> saveOrUpdateBanner(Banner banner);


	ServerResponse<Banner> manageBannerDetail(Integer bannerId);


	ServerResponse<List<List<String>>> getKeywordsList();


	ServerResponse<List<Keywords>> getKeywordsManageList();


	ServerResponse<String> saveOrUpdateKeywords(Keywords keywords);
}
