package cn.e3.order.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3.cart.service.CartService;
import cn.e3.common.utils.CookieUtils;
import cn.e3.common.utils.E3Result;
import cn.e3.common.utils.JsonUtils;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbUser;
import cn.e3.sso.service.TbUserService;
/**
 * 登录拦截
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	@Value("${SSO_URL}")
	private String SSO_URL;
	
	@Autowired
	private TbUserService tbUserService;
	
	@Autowired
	private CartService cartService;

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

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		//获取cookie，判断是否登录
		String token = CookieUtils.getCookieValue(request, "token", true);
		//没有登录
		if(StringUtils.isBlank(token)){
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());
			return false;
		}
		
		E3Result result = tbUserService.getTbUser(token);
		if(result.getStatus()!=200){
			//登录过期
			response.sendRedirect(SSO_URL+"?redirect="+request.getRequestURL());
			return false;
		}
		TbUser tbUser = (TbUser) result.getData();
		//成功登录
		request.setAttribute("tbUser", tbUser);
		//将cookie中的购物车数据存入
		String json = CookieUtils.getCookieValue(request, "cart",true);
		if(StringUtils.isNotBlank(json)){
			List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
			cartService.merageCart(tbUser.getId(), itemList);
		}
		return true;
	}

}
