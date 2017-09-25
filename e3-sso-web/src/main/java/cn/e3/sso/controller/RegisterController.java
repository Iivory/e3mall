package cn.e3.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.common.utils.E3Result;
import cn.e3.pojo.TbUser;
import cn.e3.sso.service.TbUserService;

/**
 * 用户注册
 * @author Administrator
 *
 */
@Controller
public class RegisterController {
	
	@Autowired
	private TbUserService tbUserService;
	
	/**
	 * 显示页面
	 * @return
	 */
	@RequestMapping("/page/register")
	public String showRegister(){
		return "register";
	}
	/*
	 * 校验数据
	 */
	@RequestMapping("/user/check/{parm}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String parm,@PathVariable Integer type){
		E3Result result = tbUserService.checkData(parm, type);
		return result;
	}
	
	/**
	 * 注册用户
	 */
	@RequestMapping("/user/register")
	@ResponseBody
	public E3Result register(TbUser tbUser){
		E3Result result = tbUserService.addTbUser(tbUser);
		return result;
	}
}
