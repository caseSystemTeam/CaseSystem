package com.lawer.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lawer.common.ResultGson;
import com.lawer.pojo.BusUser;
import com.lawer.pojo.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lawer.pojo.User;
import com.lawer.pojo.Password;
import com.lawer.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
	
	//跳转到登录页面
	@RequestMapping("login")
	public String Login(){
		return "html/login";
	}
	//跳转到登录页面
	@RequestMapping("register")
	public String Register(){
		return "html/register";
	}
	//判断用户是否成功登录
	@RequestMapping("index")
	@ResponseBody
	public String loginCheck(@RequestBody User us, HttpServletRequest request, HttpServletResponse response){
		User user=userService.findUser(us);  //判断数据库中是否存在该用户
		if(user!=null && us.getUsername().equals(user.getUsername())&&us.getPassword().equals(user.getPassword())){
			request.getSession().setAttribute("us",user);
			Cookie cookie = new Cookie("lawername",user.getName());
			response.addCookie(cookie);
			return "1";
		}

		return "0";
		
	}
	//跳转到主页
	@RequestMapping("toindex")
	public String toIndex(){
		return "/html/frame";
	}
	//退出登录
	@RequestMapping("logout")
	public String Logout(HttpSession httpSession){
		httpSession.invalidate();   //清除session数据
		return "/html/login";
	}

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
		user.setBusId(businessId);
        bus.setId(businessId);
        bus.setLawerid(userId);
        bus.setLawerName(busUser.getZname());
        bus.setName(busUser.getName());
        bus.setTelphone(busUser.getPhoneNumber());
		if( userService.addBusiness(bus)==0 || userService.addUser(user)==0){
			return 0;
		}

		return 1;
	}

	//检查用户名是否存在
	@RequestMapping("getAllLawer")
	@ResponseBody
	public ResultGson getAllLawer(HttpSession session){

		User user=(User)session.getAttribute("us");
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
	@RequestMapping("updateps.action")
	@ResponseBody
	public int updatePs(@RequestBody Password ps,HttpSession session,HttpServletRequest request){
		//从session中获取用户信息
		User user=(User) session.getAttribute("us");
		//将用户密码和用户输入的原密码进行比较
		if(user.getPassword().equals(ps.getPs1())){
			//将用户密码修改成新密码
			user.setPassword(ps.getPs2());
			//修改数据库中的用户密码
			int i=userService.updatePs(user);
			session.setAttribute("us", user);
			return i;
		}
		return 0;
	}
	//跳转到修改密码页面
	@RequestMapping("update.action")
	public String Update(Password ps,HttpSession session,HttpServletRequest request){
		return "/lawpage/updateps.jsp";
	}
	//跳转到个人信息页面
	@RequestMapping("toInfor.action")
	public String toInformation(Password ps,HttpSession session,HttpServletRequest request){
		return "/lawpage/information.jsp";
	}
	//跳转到修改个人信息页面
	@RequestMapping("toupinfor.action")
	public String to(Password ps,HttpSession session,HttpServletRequest request){
		return "/lawpage/upinformation.jsp";
	}
	//修改个人信息
	@RequestMapping("upinfor.action")
	@ResponseBody
	public int upInfor(@RequestBody User user,HttpSession session,HttpServletRequest request){
		
		int i=userService.upinfor(user);
		return i;
	}

}
