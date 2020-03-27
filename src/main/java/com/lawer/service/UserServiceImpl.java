package com.lawer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import com.lawer.mapper.UserMapper;
import com.lawer.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper mapper;
	@Override
	public User findUser(User user) {
	    return mapper.findUser(user);
	   
	}

	@Override
	public int updatePs(User user) {

		return mapper.updatePs(user);
	}

	public int upinfor(User user) {

		return mapper.upinfor(user);
	}

	@Override
	public int sNum() {

		return mapper.sNum();
	}

	@Override
	public int sCount() {

		return mapper.sCount();
	}

	@Override
	public List<User> getList() {

		return mapper.getList();
	}

	@Override
	public int IdByname(String name) {

		
		return mapper.IdByname(name);
	}

	@Override
	public String nameById(int lawerid) {

		return mapper.nameById(lawerid);
	}

	@Override
	public User userById(int id) {

		return mapper.userById(id);
	}

}
