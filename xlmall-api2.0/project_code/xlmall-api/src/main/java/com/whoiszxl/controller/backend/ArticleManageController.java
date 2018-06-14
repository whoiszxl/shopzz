package com.whoiszxl.controller.backend;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.entity.Keywords;
import com.whoiszxl.service.ArticleService;
import com.whoiszxl.vo.BannerVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 管理员公共文章模块
 * @author whoiszxl
 *
 */
@Api(value="管理员公共文章模块",description="管理员公共文章模块")
@RestController
@RequestMapping("/manage/article")
public class ArticleManageController {
	
	@Autowired
	private ArticleService articleService;
	
	@PostMapping("banner_list")
	@ApiOperation(value = "后台banner列表")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<List<Banner>> bannerList() {
		return articleService.getBannerManageList(Const.Article.BANNER_LIST_COUNT);
	}
	
	@PostMapping("banner_save")
	@ApiOperation(value = "后台banner保存")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<String> bannerSave(Banner banner) {
		return articleService.saveOrUpdateBanner(banner);
	}
	
	@PostMapping("banner_detail")
	@ApiOperation(value = "后台banner詳情")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<BannerVo> bannerDetail(Integer bannerId) {
		return articleService.manageBannerDetail(bannerId);
	}
	
	@PostMapping("keywords_list")
	@ApiOperation(value = "后台keywords列表")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<List<Keywords>> keywordsList() {
		return articleService.getKeywordsManageList();
	}
	
	@PostMapping("keywords_save")
	@ApiOperation(value = "后台keywords保存")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<String> bannerSave(Keywords keywords) {
		return articleService.saveOrUpdateKeywords(keywords);
	}
}
