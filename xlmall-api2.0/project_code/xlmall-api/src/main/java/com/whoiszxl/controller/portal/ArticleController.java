package com.whoiszxl.controller.portal;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Keywords;
import com.whoiszxl.service.ArticleService;
import com.whoiszxl.utils.AliSmsSender;
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
	public ServerResponse<List<BannerVo>> bannerList() {
		List<BannerVo> bannerList = articleService.getBannerList(Const.Article.BANNER_LIST_COUNT);
		return ServerResponse.createBySuccess(bannerList);
	}

	@GetMapping("keywords")
	@ApiOperation(value = "獲取主頁關鍵詞")
	public ServerResponse<List<List<String>>> keywordsList() {
		return articleService.getKeywordsList();
	}
	
	@GetMapping("sms")
	@ApiOperation(value = "发一个短信")
	public ServerResponse<String> sendSMS(String phoneNum) {
		SendSmsResponse sendSms = null;
		try {
			sendSms = AliSmsSender.sendNoTemplateSMS(phoneNum);
			return ServerResponse.createBySuccessMessage("发送成功");
		} catch (ClientException e) {
			return ServerResponse.createBySuccessMessage("发送失败");
		}
	}
}
