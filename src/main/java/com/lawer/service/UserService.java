package com.lawer.service;

import java.util.List;
import java.util.Map;

import com.lawer.pojo.Business;
import com.lawer.pojo.User;
import com.lawer.util.ResponseVo;

public interface UserService {
	public User findUser(User user);   //查找用户
	public int updatePs(User user);  //修改密码
	public int upinfor(User user);  //修改个人信息
	public int sNum();  //查询最近已分配案件的律师所在位置
	public int sCount();  //查询律师总数
	public List<User> getList();  //查询所有的律师信息
	public int IdByname(String name);  //通过姓名查询id
	public String nameById(String lawerid);  //通过id查询name
	public User userById(String id);  //通过id查询用户信息
	public String checkUserName(String username); //检查用户名是否存在
	public int addBusiness(Business bus); //添加律所信息
	public int addUser(User user); //添加用户信息
	public List<Map<String,Object>> getAllLawer(String busId); //查询当前律所的所有律师
	public Map<String,Object> getUserInfo(User user);   //封装用户信息
	public List<Map<String,Object>> getBusinessUser(Map<String,Object> map);  //获取公司当前所有员工信息
	public int getBusUserCount(Map<String,Object> map);  //获取员工数量
	public int addUser(Map<String,Object> map); //添加用户
	public void deleteUser(String id); //删除用户
	public List<Map<String,Object>> getAllBusiness(); //获取所有律所信息
	public Map<String,Object>  getBusinessInfo(String id); //获取律所信息
	/**
	 * 根据用户id分配角色集合
	 * @param userId
	 * @param roleIds
	 * @return int
	 */
	int addAssignRole(String userId, List<String> roleIds);

	/**
	 * 通过用户id查询角色信息
	 * @param id
	 * @return
	 */
	Map<String,Object> selectRoleByUserId(String id);

	/**
	 * 通过用户username查询用户信息
	 * @param username
	 * @return
	 */
	User getByusername(String username);
}
