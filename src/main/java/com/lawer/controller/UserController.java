package com.lawer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lawer.common.DateUtil;
import com.lawer.common.IpAdress;
import com.lawer.common.ResultGson;
import com.lawer.pojo.*;
import com.lawer.service.CaseListService;
import com.lawer.service.LogService;
import com.lawer.service.PermissionService;
import com.lawer.shiro.MyShiroRealm;

import com.lawer.util.PasswordHelper;

import com.lawer.util.ResponseVo;
import com.lawer.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lawer.service.UserService;

import java.io.Serializable;
import java.util.*;

/**
 * 用户管理
 * @author 张涛
 * date: 2020.3.30 14:23:48
 */
@Controller
@RequestMapping("userCon")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private LogService logService;
	@Autowired
	private CaseListService caseListService;
	@Autowired
	private MyShiroRealm shiroRealm;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RedisCacheManager redisCacheManager;

	//跳转到登录页面
	@RequestMapping("login")
	public String Login(){
		return "html/login";
	}
	//跳转到注册页面
	@RequestMapping("register")
	public String Register(){
		return "html/register";
	}
	//判断用户是否成功登录
	@RequestMapping("index")
	@ResponseBody
	public String loginCheck(@RequestBody User us, HttpServletRequest request, HttpServletResponse response){

		UsernamePasswordToken token = new UsernamePasswordToken(us.getUsername(), us.getPassword());
		try{
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			User user=(User)SecurityUtils.getSubject().getPrincipal();
		/*	us.setPassword("");
			User user=userService.findUser(us);*/
			request.getSession().setAttribute("us",user);
			Log log =Log.ok(user.getUsername(),IpAdress.getIp(request),0,"登录","成功","",user.getBusId());
			logService.addLog(log);
		} catch (LockedAccountException e) {
			token.clear();
			Log log =Log.ok(us.getUsername(),IpAdress.getIp(request),0,"登录","失败","","");
			logService.addLog(log);
			return "0";
		} catch (AuthenticationException e) {
			token.clear();
			Log log =Log.ok(us.getUsername(),IpAdress.getIp(request),0,"登录","失败","","");
			logService.addLog(log);
			return "0";
		}
		return "1";
	}
	//跳转到主页
	@RequestMapping("toindex")
	public String toIndex(){
		return "/html/frame";
	}
	//退出登录
//	@RequestMapping("logout")
//	public String Logout(HttpSession httpSession){
//		httpSession.invalidate();   //清除session数据
//		return "/html/login";
//	}

	//检查用户名是否存在
	@RequestMapping("checkName")
	@ResponseBody
	public String checkUserName(String username,HttpServletRequest request){

		return userService.checkUserName(username);
	}

	//企业用户注册
	@RequestMapping(value = "addBusUser")
	@ResponseBody
	public int addBusUser(@RequestBody BusUser busUser,HttpServletRequest request){
		String userId = UUID.randomUUID().toString();
		String businessId = UUID.randomUUID().toString();
		User user =new User();
		Business bus = new Business();
		user.setId(userId);
		user.setUsername(busUser.getUsername());
		user.setName(busUser.getZname());
		user.setPassword(busUser.getPassword());
		user.setGender(busUser.getGender());
		user.setPhonenumber(busUser.getPhoneNumber());
		user.setPosition("高级律师");
		user.setBusId(businessId);
		user.setCreateTime(DateUtil.getToday());
		PasswordHelper.encryptPassword(user);
		bus.setId(businessId);
		bus.setLawerid(userId);
		bus.setLawerName(busUser.getZname());
		bus.setName(busUser.getName());
		bus.setTelphone(busUser.getPhoneNumber());
		List<String> roleIdsList =new ArrayList<>();
		roleIdsList.add("1");
		if( userService.addBusiness(bus)==0 || userService.addUser(user)==0 || userService.addAssignRole(user.getId(),roleIdsList)==0){

			return 0;
		}
		List<String> userIds = new ArrayList<>();
		userIds.add(userId);
		shiroRealm.clearAuthorizationByUserId(userIds);
		return 1;
	}

	//添加用户
	@RequestMapping(value = "addUser")
	@ResponseBody
	public int addUser(@RequestBody  String json,HttpServletRequest request){
//		User user=(User)session.getAttribute("us");
		User user=(User)SecurityUtils.getSubject().getPrincipal();
		Map<String,Object> map = JSON.parseObject(json);
		map.put("busid",user.getBusId());
		map.put("create_time", DateUtil.getToday());
		try{
			userService.addUser(map);
		}catch (Exception e){
			Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),0,"添加用户","失败", "添加用户\""+ map.get("username")+"\"",user.getBusId());
			logService.addLog(log);
			return 0;
		}
		Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),0,"添加用户","成功", "添加用户\""+ map.get("username")+"\"",user.getBusId());
		logService.addLog(log);
		return 1;
	}


	//获取当前律所所有律师
	@RequestMapping("getAllLawer")
	@ResponseBody
	public ResultGson getAllLawer(){

		User user=(User)SecurityUtils.getSubject().getPrincipal();
		String busId=user.getBusId();
		List<Map<String,Object>> list=null;
		try{
			list = userService.getAllLawer(busId);
		}catch (Exception e){
			return ResultGson.error("操作失败");
		}
		Map<String,Object> map = new HashMap<>();
		map.put("data",list);

		return ResultGson.ok(map);
	}
	//修改密码
	@RequestMapping("updatePs")
	@ResponseBody
	public int updatePs(@RequestBody String json,HttpSession session,HttpServletRequest request){

		User user = (User)session.getAttribute("us");
		User us = userService.userById(user.getId());
		Map<String, Object> mapJson = JSON.parseObject(json);
		String pw=us.getPassword();
		us.setPassword((String) mapJson.get("password"));
		String npw =PasswordHelper.getPassword(us);

		//将用户密码和用户输入的原密码进行比较
		if(pw.equals(npw)){
			//将用户密码修改成新密码
			us.setPassword((String) mapJson.get("password"));
			//修改数据库中的用户密码
			PasswordHelper.encryptPassword(us);

			try{
				userService.updatePs(us);
			}catch (Exception e){
				Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),0,"修改密码","失败", "无",user.getBusId());
				logService.addLog(log);
				return -2;
			}
			session.setAttribute("us",us);
			Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),0,"修改密码","成功", "无",user.getBusId());
			logService.addLog(log);

			return 1;
		}
		Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),0,"修改密码","失败", "无",user.getBusId());
		logService.addLog(log);
		return 0;
	}

	//通过手机验证码修改密码
	@RequestMapping("updateVerPs")
	@ResponseBody
	public ResultGson updateVerPs(@RequestBody String json,HttpSession session,HttpServletRequest request){
		Map<String, Object> mapJson = JSON.parseObject(json);
		String username = (String)mapJson.get("username");
		String verifyCode = (String)mapJson.get("verifyCode");
		String password = (String)mapJson.get("password");
		String phone = (String)mapJson.get("phoneNumber");
		String code =(String) session.getAttribute("code");

		if(code==null || "".equals(code)){
			return ResultGson.error("请先获取验证码");
		}
		if(!code.equals(verifyCode)){
			return ResultGson.error("验证码错误，请输入正确的验证码");
		}
        User us = userService.getByusername(username);
		if(!us.getPhonenumber().equals(phone)){
			return  ResultGson.error("当前用户名和手机号不匹配");
		}
		us.setPassword(password);
		PasswordHelper.encryptPassword(us);
		try{
			userService.updatePs(us);
		}catch (Exception e){
			Log log =Log.ok(us.getUsername(), IpAdress.getIp(request),0,"修改密码","失败", "无",us.getBusId());
			logService.addLog(log);
			return ResultGson.error("修改失败");
		}
		session.setAttribute("code","");
		Log log =Log.ok(us.getUsername(), IpAdress.getIp(request),0,"修改密码","成功", "无",us.getBusId());
		logService.addLog(log);
		return ResultGson.ok();
	}


	//获取用户信息
	@RequestMapping("getUserInfo")
	@ResponseBody
	public ResultGson getUserInfo(HttpSession session){
		//从session中获取用户信息
		User user=(User) session.getAttribute("us");
		try{
			Map<String,Object> map=userService.getUserInfo(user);
			return ResultGson.ok(map);
		}catch (Exception e){
			return ResultGson.error("获取信息失败");
		}
	}
	//获取用户信息
	@RequestMapping("getBusinessUser")
	@ResponseBody
	public String getBusinessUser(String page,String limit, String key, HttpSession session){
		//从session中获取用户信息
		User user=(User) session.getAttribute("us");
		Map<String,Object> map = new HashMap<String,Object>();
		if (key != null) {
			JSONObject json = JSONObject.parseObject(key);
			if (!("".equals(json.get("name"))) && !(null == json.get("name"))) {
				map.put("name", json.getString("name"));
			}
			if (!("".equals(json.get("position"))) && !(null == json.get("position"))) {
				if(json.getIntValue("position")==0){
					map.put("position","初级律师");
				}
				else if(json.getIntValue("position")==1){
					map.put("position","中级律师");
				}
				else if(json.getIntValue("position")==2){
					map.put("position","高级律师");
				}
			}
		}
		map.put("busid",user.getBusId());
		int pageSize = Integer.parseInt(limit);
		map.put("pageSize", pageSize);
		map.put("skipCount", (Integer.parseInt(page) - 1) * pageSize);
		List list = userService.getBusinessUser(map);
		int count = userService.getBusUserCount(map);
		JSONObject result = new JSONObject();
		result.put("data",list);
		result.put("msg","请求成功");
		result.put("code", 0);
		result.put("count",count);
		return JSON.toJSONString(result);
	}


	//修改个人信息
	@RequestMapping("updateUser")
	@ResponseBody
	public int updateUser(@RequestBody User user,HttpSession session,HttpServletRequest request){
		//从session中获取用户信息
		String id=(String) session.getAttribute("mdfId");
		//从session中获取用户信息
		User us=(User) session.getAttribute("us");
		user.setId(id);
		try{
			int i=userService.upinfor(user);
			Log log =Log.ok(us.getUsername(), IpAdress.getIp(request),0,"修改信息","成功", "修改"+user.getUsername()+"的个人信息",us.getBusId());
			logService.addLog(log);
			return i;
		}catch (Exception e){
			Log log =Log.ok(us.getUsername(), IpAdress.getIp(request),0,"修改信息","失败", "修改"+user.getUsername()+"的个人信息",us.getBusId());
			logService.addLog(log);
			return 0;
		}

	}

	//获取用户信息
	@RequestMapping("getUserById")
	@ResponseBody
	public ResultGson getUserById(HttpSession session,HttpServletRequest request){
		//从session中获取用户信息
		String id=(String) session.getAttribute("mdfId");
		User user=userService.userById(id);
		user.setPassword("");
		Map<String,Object> map = new HashMap<>();
		map.put("result",user);
		return ResultGson.ok(map);
	}

	//删除用户信息
	@RequestMapping("deleteUser")
	@ResponseBody
	public ResultGson deleteUser(@RequestBody String json,HttpServletRequest request,HttpSession session) {
		//从session中获取用户信息
		User user=(User) session.getAttribute("us");
		Map<String,Object> map = JSON.parseObject(json);
		User us = userService.userById((String) map.get("id"));
		Map<String,Object> bmap = userService.getBusinessInfo(user.getBusId());
		if(bmap.get("lawerid").equals(map.get("id"))){
			Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),0,"删除用户","失败", "无法删除超级管理员",user.getBusId());
			logService.addLog(log);
			return ResultGson.error("无法删除超级管理员");
		}
		map.put("busId",user.getBusId());
		map.put("jstatus",0);
		int i=caseListService.getACaseCount(map);
		if(i>0){
			Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),0,"删除用户","失败", "用户还有未完成案件，无法删除用户\""+us.getUsername()+"\"",user.getBusId());
			logService.addLog(log);
			return ResultGson.error("未完成");
		}
		try {
			userService.deleteUser((String) map.get("id"));
			Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),0,"删除用户","成功", "删除用户\""+us.getUsername()+"\"",user.getBusId());
			logService.addLog(log);
			return ResultGson.ok("删除成功");
		} catch (Exception e) {
			Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),0,"删除用户","失败", "删除用户\""+us.getUsername()+"\"",user.getBusId());
			logService.addLog(log);
			return ResultGson.error("删除失败");
		}
	}

	/*获取当前登录用户的菜单*/
	@RequestMapping("menu")
	@ResponseBody
	public List<Permission> getMenus(){
		List<Permission> permissionListList = permissionService.selectMenuByUserId(((User) SecurityUtils.getSubject().getPrincipal()).getId());
		return permissionListList;
	}
	/*登出*/
	@RequestMapping("logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		if(null!=subject){
			String username = ((User) SecurityUtils.getSubject().getPrincipal()).getUsername();
			Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
			Cache<String, Deque<Serializable>> cache = redisCacheManager.getCache(redisCacheManager.getKeyPrefix()+username);
			Deque<Serializable> deques = cache.get(username);
			for(Serializable deque : deques){
				if(sessionId.equals(deque)){
					deques.remove(deque);
					break;
				}
			}
			cache.put(username,deques);
		}
		subject.logout();
		return "html/login";
	}






}
