package cn.e3.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3.common.utils.CookieUtils;
import cn.e3.common.utils.E3Result;
import cn.e3.common.utils.JsonUtils;
import cn.e3.pojo.TbUser;
import cn.e3.sso.service.TbUserService;
/**
 * 登陆处理请求拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private TbUserService tbUserService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 判断用户是否登录
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//从cookie中获取信息
		String token = CookieUtils.getCookieValue(request, "token");
		//判断是否登录,未登录放行
		if(StringUtils.isBlank(token)){
			return true;
		}
		//判断是否登陆过期
		E3Result e3Result = tbUserService.getTbUser(token);
		if(e3Result.getStatus()!=200){
			return true;
		}
		
		//登录后,将信息放入request
		TbUser tbUser = (TbUser) e3Result.getData();
		request.setAttribute("tbUser", tbUser);
		return true;
	}

}
