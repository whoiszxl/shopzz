package com.whoiszxl.controller.portal;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.service.ArticleService;
import com.whoiszxl.vo.BannerVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 前台公共文章模块
 * @author whoiszxl
 *
 */
@Api(value = "前台公共文章模块",description="前台公共文章模块")
@RestController
@RequestMapping("/article/")
public class ArticleController {

	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;
	
	
	@GetMapping("banners")
	@ApiOperation(value = "获取轮播图")
	public ServerResponse bannerList() {
		List<BannerVo> bannerList = articleService.getBannerList(Const.Article.BANNER_LIST_COUNT);
		return ServerResponse.createBySuccess(bannerList);
	}

}
