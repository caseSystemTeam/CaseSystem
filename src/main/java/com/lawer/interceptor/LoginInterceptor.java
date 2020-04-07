package com.lawer.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lawer.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		String url=arg0.getRequestURI();
		//当页面处于登录页面和进行登录判断时允许通过

		System.out.println(url);
		if(url.indexOf("login")>=0||url.indexOf("register")>=0 ||(url.indexOf("index")>=0&&url.indexOf("toindex")<0)){
			return true;
		}

		//从session中获取用户信息
		User user=(User)arg0.getSession().getAttribute("us");
		if(user!=null){
			return true;
		}

		arg1.sendRedirect("/userCon/login");
		return false;
	}

}
