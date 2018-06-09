package com.whoiszxl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.whoiszxl.dao.BannerMapper;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.service.ArticleService;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.vo.BannerVo;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private BannerMapper bannerMapper;
	
	@Override
	public List<BannerVo> getBannerList(int num) {
		List<Banner> banners = bannerMapper.selectBannersByNum(num);
		ArrayList<BannerVo> bannerVoList = Lists.newArrayList();
		for (Banner banner : banners) {
			BannerVo bannerVo = new BannerVo();
			BeanUtils.copyProperties(banner, bannerVo);
			bannerVo.setImgurl(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://image.chenyuspace.com/")+bannerVo.getImgurl());
			bannerVoList.add(bannerVo);
		}
		return bannerVoList;
	}

	@Override
	public List<Banner> getBannerManageList(int num) {
		List<Banner> banners = bannerMapper.selectBannersByNum(num);
		for (Banner banner : banners) {
			banner.setImgurl(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://image.chenyuspace.com/")+banner.getImgurl());
		}
		return banners;
	}
	
	

}