package com.lawer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lawer.pojo.User;
import com.lawer.pojo.Password;
import com.lawer.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService loginService;
	
	//跳转到登录页面
	@RequestMapping("login.action")
	public String Login(){
		return "jsp/login.jsp";
	}
	//判断用户是否成功登录
	@RequestMapping("index.action")
	public String loginCheck(User us,HttpServletRequest request){
		User user=loginService.findUser(us);  //判断数据库中是否存在该用户
		if(user!=null){
			request.getSession().setAttribute("us", user);
			return "/jsp/index.jsp";
			
		}
		String error="用户名或密码错误";
		request.setAttribute("error", error);
		return "/jsp/login.jsp";
		
	}
	//跳转到主页
	@RequestMapping("toindex.action")
	public String toIndex(User us,HttpServletRequest request){
		
		return "/jsp/index.jsp";
		
	}
	//退出登录
	@RequestMapping("logout.action")
	public String Logout(HttpSession httpSession){
		httpSession.invalidate();   //清除session数据
		return "/jsp/login.jsp";
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
			int i=loginService.updatePs(user);
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
		
		int i=loginService.upinfor(user);
		return i;
	}

}
