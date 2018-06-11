package com.whoiszxl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.whoiszxl.common.ServerResponse;
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

	@Override
	public ServerResponse<String> saveOrUpdateProduct(Banner banner) {
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
	public ServerResponse<Banner> manageBannerDetail(Integer bannerId) {
		Banner banner = bannerMapper.selectByPrimaryKey(bannerId);
		if(banner == null) {
			return ServerResponse.createByErrorMessage("轮播图不存在");
		}
		banner.setImgurl(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://image.chenyuspace.com/")+banner.getImgurl());
		return ServerResponse.createBySuccess(banner);
	}
	
	

}