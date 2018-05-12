package com.whoiszxl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whoiszxl.dao.SysUserMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionApplicationTests {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Test
	public void contextLoads() {
		
	}
	
	@Test
	public void mybatisTest() {
		System.out.println(sysUserMapper.checkEmailExist("whoiszxl@gmail.com", null));
	}

}
