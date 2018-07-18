package com.whoiszxl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.dao.BannerMapper;
import com.whoiszxl.dao.UserMapper;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.entity.Keywords;
import com.whoiszxl.entity.User;
import com.whoiszxl.service.ArticleService;
import com.whoiszxl.utils.AliSmsSender;
import com.whoiszxl.vo.BannerVo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BannerMapper bannerMapper;
	
	@Autowired
	private ArticleService articleService;
	
	@Test
	public void contextLoads() {
	}
	
	
	
	@Test
	public void mybatisTest() {
		User selectByPrimaryKey = userMapper.selectByPrimaryKey(1);
		System.out.println(selectByPrimaryKey);
		
		List<BannerVo> banner = articleService.getBannerList(5);
		
		System.out.println(banner);
		
	}
	
	
	@Test
	public void testname() throws Exception {
		ServerResponse<List<List<String>>> keywordsList = articleService.getKeywordsList();
		System.out.println(keywordsList.getData());
	}
	
	@Test
	public void testaaa() throws Exception {
		List<User> selectAllUser = userMapper.selectAllUser();
		for (User user : selectAllUser) {
			System.out.println(user);
		}
	}

	
	@Test
	public void message() throws Exception {
		
		SendSmsResponse sendSms = AliSmsSender.sendNoTemplateSMS("17688900411");
		System.out.println("send sms:"+sendSms.getMessage());
	}
}
