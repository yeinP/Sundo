package com.example.demo.service;

import com.example.demo.dto.account.UserDto;
import com.example.demo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
	
	private final UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	public int getUserCount() {
		return this.userDao.count();
	}

	public String getUserRole(String id) {
		return this.userDao.selectUserRole(id);
	}

	public Integer isUser(String id) {
		return this.userDao.isUser(id);
	}

	public UserDto checkUser(String id) {
		return this.userDao.selectOne(id);
	}

	public int registerUser(UserDto userDto) {
		return this.userDao.insertUser(userDto);
	}

}
