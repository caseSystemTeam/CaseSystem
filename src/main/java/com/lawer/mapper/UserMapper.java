package com.lawer.mapper;

import com.lawer.pojo.Business;
import com.lawer.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface UserMapper {
	public User findUser(User user);//查找用户
	public int updatePs(User user);  //修改密码
	public int upinfor(User user);  //修改个人信息
	public int sNum();  //查询最近已分配案件的律师所在位置
	public int sCount();  //查询律师总数
	public List<User> getList();  //查询所有的律师信息
	public int IdByname(String name);  //通过姓名查询id
	public String nameById(int lawerid);  //通过id查询name
	public User userById(String id);  //通过id查询用户信息
	public User checkUserName(String username); //检查用户名是否存在
	public int addBusiness(Business bus); //添加律所信息
	public int addUser(User user); //添加用户信息
	public List<Map<String,Object>> getAllLawer(String busId); //查询当前律所的所有律师

}
