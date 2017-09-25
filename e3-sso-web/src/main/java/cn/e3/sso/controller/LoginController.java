package cn.e3.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.common.utils.CookieUtils;
import cn.e3.common.utils.E3Result;
import cn.e3.common.utils.JsonUtils;
import cn.e3.sso.service.TbUserService;

/**
 * 用户登陆
 * @author Administrator
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	private TbUserService tbUserService;
	/**
	 * 展示页面
	 * @return
	 */
	@RequestMapping("/page/login")
	public String showLogin(String redirect,Model model){
		model.addAttribute("redirect", redirect);
		return "login";
	}
	
	@RequestMapping("/user/login")
	@ResponseBody
	public E3Result login(String username,String password,
			HttpServletRequest request,HttpServletResponse response){
		E3Result e3Result = tbUserService.login(username, password);
		String token = e3Result.getData().toString();
		CookieUtils.setCookie(request, response, "token", token);
		return e3Result;
	}
	
	@RequestMapping(value="/user/token/{token}",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback){
		E3Result result = tbUserService.getTbUser(token);
		if(StringUtils.isNoneBlank(callback)){
			String strResult = callback+"("+JsonUtils.objectToJson(result)+");";
			return strResult;
		}
		return JsonUtils.objectToJson(result);
	}
	
	@RequestMapping("/page/logout/{token}")

	public String logout(@PathVariable String token,HttpServletRequest request,HttpServletResponse response){
		tbUserService.logout(token);
		CookieUtils.deleteCookie(request, response, "token");
		return "login";
	}
	

	
	
}
