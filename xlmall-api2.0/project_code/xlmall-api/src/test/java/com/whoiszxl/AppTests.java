package com.whoiszxl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whoiszxl.dao.BannerMapper;
import com.whoiszxl.dao.UserMapper;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BannerMapper bannerMapper;
	
	@Test
	public void contextLoads() {
	}
	
	
	
	@Test
	public void mybatisTest() {
		User selectByPrimaryKey = userMapper.selectByPrimaryKey(1);
		System.out.println(selectByPrimaryKey);
		
		Banner banner = bannerMapper.selectByPrimaryKey(1);
		System.out.println(banner);
		
	}
	
	
	

}
