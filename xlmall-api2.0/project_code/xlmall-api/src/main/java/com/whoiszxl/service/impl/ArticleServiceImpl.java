package com.whoiszxl.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.dao.BannerMapper;
import com.whoiszxl.dao.KeywordsMapper;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.entity.Keywords;
import com.whoiszxl.service.ArticleService;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.utils.RedisPoolUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;
import com.whoiszxl.vo.BannerVo;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private BannerMapper bannerMapper;
	
	@Autowired
	private KeywordsMapper keywordsMapper;
	
	private static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	@Override
	public List<BannerVo> getBannerList(int num) {
		List<Banner> banners = bannerMapper.selectBannersByNum(num);
		ArrayList<BannerVo> bannerVoList = Lists.newArrayList();
		for (Banner banner : banners) {
			BannerVo bannerVo = new BannerVo();
			BeanUtils.copyProperties(banner, bannerVo);
			bannerVo.setImgurl(PropertiesUtil.getProperty("ftp.server.http.prefix")+bannerVo.getImgurl());
			bannerVoList.add(bannerVo);
		}
		return bannerVoList;
	}

	@Override
	public ServerResponse<List<Banner>> getBannerManageList(int num) {
		List<Banner> banners = bannerMapper.selectBannersByNum(num);
		for (Banner banner : banners) {
			banner.setImgurl(PropertiesUtil.getProperty("ftp.server.http.prefix")+banner.getImgurl());
		}
		return ServerResponse.createBySuccess(banners);
	}

	@Override
	public ServerResponse<String> saveOrUpdateBanner(Banner banner) {
		if (banner != null) {
			if (banner.getId() != null) {
				banner.setUpdateTime(new Date());
				int rowCount = bannerMapper.updateByPrimaryKey(banner);
				if (rowCount > 0) {
					return ServerResponse.createBySuccessMessage("更新轮播图成功");
				} else {
					return ServerResponse.createByErrorMessage("更新轮播图失败");
				}

			} else {
				banner.setUpdateTime(new Date());
				banner.setUpdateTime(new Date());
				int rowCount = bannerMapper.insert(banner);
				if (rowCount > 0) {
					return ServerResponse.createBySuccessMessage("新增轮播图成功");
				} else {
					return ServerResponse.createByErrorMessage("新增轮播图失败");
				}
			}
		}
		return ServerResponse.createByErrorMessage("新增或更新轮播图参数不正确了");
	}

	@Override
	public ServerResponse<BannerVo> manageBannerDetail(Integer bannerId) {
		Banner banner = bannerMapper.selectByPrimaryKey(bannerId);
		if(banner == null) {
			return ServerResponse.createByErrorMessage("轮播图不存在");
		}
		BannerVo bannerVo = new BannerVo();
		BeanUtils.copyProperties(banner, bannerVo);
		bannerVo.setImgHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
		return ServerResponse.createBySuccess(bannerVo);
	}

	@Override
	public ServerResponse<List<List<String>>> getKeywordsList() {
		//先從redis查詢是否存在keywords的緩存
		String keywords_cache = RedisShardedPoolUtil.get(Const.Article.INDEX_KEYWORDS_REDIS_KEY);
		//存在，直接從緩存拿
		List<List<String>> result = null;
		if(keywords_cache != null) {
			logger.info("從redis取出了keyword");
			result = JsonUtil.string2Obj(keywords_cache, List.class);
		}else {
			logger.info("從數據庫取出了keyword");
			//不存在，從數據庫拿
			List<Keywords> keywords = keywordsMapper.selectAllKeywords();
			result = new ArrayList<>();
			for (Keywords keyword : keywords) {
				String[] splitKeyword = keyword.getWords().split(",");
				List<String> keywordList = Arrays.asList(splitKeyword);
				result.add(keywordList);
			}
			//需要緩存到redis中
			RedisShardedPoolUtil.set(Const.Article.INDEX_KEYWORDS_REDIS_KEY, JsonUtil.obj2String(result));
		}
		
		return ServerResponse.createBySuccess(result);
		
	}

	@Override
	public ServerResponse<List<Keywords>> getKeywordsManageList() {
		List<Keywords> keywords = keywordsMapper.selectAllKeywords();
		return ServerResponse.createBySuccess(keywords);
	}

	@Override
	public ServerResponse<String> saveOrUpdateKeywords(Keywords keywords) {
		if (keywords != null) {
			if (keywords.getId() != null) {
				int rowCount = keywordsMapper.updateByPrimaryKey(keywords);
				if (rowCount > 0) {
					//更新或新增成功之后需要更新redis缓存，直接删掉好了
					RedisShardedPoolUtil.del(Const.Article.INDEX_KEYWORDS_REDIS_KEY);
					return ServerResponse.createBySuccessMessage("更新关键词成功");
				} else {
					return ServerResponse.createByErrorMessage("更新关键词失败");
				}

			} else {
				int rowCount = keywordsMapper.insert(keywords);
				if (rowCount > 0) {
					//更新或新增成功之后需要更新redis缓存，直接删掉好了
					RedisShardedPoolUtil.del(Const.Article.INDEX_KEYWORDS_REDIS_KEY);
					return ServerResponse.createBySuccessMessage("新增关键词成功");
				} else {
					return ServerResponse.createByErrorMessage("新增关键词失败");
				}
			}
		}
		return ServerResponse.createByErrorMessage("新增或更新关键词参数不正确了");
	}
	
	

}