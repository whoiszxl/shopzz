package com.whoiszxl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.dao.BannerMapper;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private BannerMapper bannerMapper;
	
	@Override
	public List<Banner> getBannerList(int num) {
		List<Banner> banners = bannerMapper.selectBannersByNum(num);
		return banners;
	}

}