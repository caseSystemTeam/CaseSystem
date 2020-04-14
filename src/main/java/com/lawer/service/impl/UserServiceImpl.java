package com.lawer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.lawer.mapper.BusinessMapper;
import com.lawer.pojo.Business;
import com.lawer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


import com.lawer.mapper.UserMapper;
import com.lawer.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper mapper;
	@Autowired
	private BusinessMapper businessMapper;
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
	public User userById(String id) {

		return mapper.userById(id);
	}

	@Override
	public String checkUserName(String username) {
		User user = mapper.checkUserName(username);
		if(user!=null){
			return "1";
		}
		return "0";
	}

	@Override
	public int addBusiness(Business bus) {
		try{
			mapper.addBusiness(bus);
		}catch (Exception e){
			//添加失败
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int addUser(User user) {
		try{
			mapper.addUser(user);
		}catch (Exception e){
			//添加失败
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List<Map<String, Object>> getAllLawer(String busId) {
		return mapper.getAllLawer(busId);
	}

	@Override
	public Map<String, Object> getUserInfo(User user) {
		Map<String,Object> bmap = businessMapper.getBusinessById(user.getBusId());
		Map<String,Object> map =  new HashMap<>();
		user.setPassword("");
		map.put("user",user);
		map.put("business",bmap);
		return map;
	}

	@Override
	public List<Map<String, Object>> getBusinessUser(Map<String, Object> map) {

		return businessMapper.getBusinessUser(map);
	}

	@Override
	public int addUser(Map<String, Object> map) {
		User user = new User();
		user.setUsername((String) map.get("username"));
		user.setPassword((String) map.get("password"));
		user.setName((String) map.get("name"));
		user.setPosition((String)map.get("position"));
		user.setBusId((String)map.get("busid"));
		user.setGender((String)map.get("gender"));
		user.setId(UUID.randomUUID().toString());
		user.setPhonenumber((String) map.get("phonenumber"));
		return mapper.addUser(user);
	}

}
