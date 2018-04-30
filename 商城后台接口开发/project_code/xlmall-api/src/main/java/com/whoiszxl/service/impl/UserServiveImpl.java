package com.whoiszxl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.entity.User;
import com.whoiszxl.repo.primary.UserRepository;
import com.whoiszxl.service.UserService;

@Service
public class UserServiveImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}

}
