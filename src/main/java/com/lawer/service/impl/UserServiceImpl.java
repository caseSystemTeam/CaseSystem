package com.lawer.service.impl;

import java.util.*;

import com.lawer.mapper.BusinessMapper;
import com.lawer.mapper.RoleMapper;
import com.lawer.mapper.UserRoleMapper;
import com.lawer.pojo.Business;
import com.lawer.pojo.UserRole;
import com.lawer.service.UserService;
import com.lawer.util.MD5Utils;
import com.lawer.util.PasswordHelper;
import com.lawer.util.ResponseVo;
import com.lawer.util.ResultUtil;
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
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public User findUser(User user) {
	    return mapper.findUser(user);
	   
	}

	@Override
	public int updatePs(User user) {

		return mapper.upinfor(user);
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
	public String nameById(String lawerid) {

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
		User us = userById(user.getId());
		user.setPassword("");
		map.put("user",us);
		map.put("business",bmap);
		return map;
	}

	@Override
	public List<Map<String, Object>> getBusinessUser(Map<String, Object> map) {
		List<Map<String,Object>> list = businessMapper.getBusinessUser(map);
		for(Map<String,Object> lmap:list){
			Map<String,Object> rolemapper = mapper.selectRoleByUserId((String)lmap.get("Id"));
			if(rolemapper!=null && rolemapper.size()!=0){
				lmap.put("role",rolemapper.get("name"));
			}
		}
		return list;
	}

	@Override
	public int getBusUserCount(Map<String, Object> map) {
		return businessMapper.getBusUserCount(map);
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
		user.setCreateTime((String) map.get("create_time"));
		PasswordHelper.encryptPassword(user);
		UserRole urole = new UserRole();
		urole.setUserId(user.getId());
		urole.setRoleId((String)map.get("urole"));
		userRoleMapper.insert(urole);
		return mapper.addUser(user);
	}

	@Override
	public void deleteUser(String id) {
        businessMapper.deleteUser(id);
	}

	@Override
	public List<Map<String, Object>> getAllBusiness() {

		return businessMapper.getAllBusiness();
	}

	@Override
	public Map<String, Object> getBusinessInfo(String id) {
		return businessMapper.getBusinessInfo(id);
	}

	@Override
	public int addAssignRole(String userId, List<String> roleIds) {
		try{
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRoleMapper.delete(userRole);
			for(String roleId :roleIds){
				userRole.setUserId(userId);
				userRole.setRoleId(roleId);
				userRoleMapper.insert(userRole);
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Map<String, Object> selectRoleByUserId(String id) {
		return mapper.selectRoleByUserId(id);
	}

	@Override
	public User getByusername(String username) {
		return mapper.getByusername(username);
	}

}
