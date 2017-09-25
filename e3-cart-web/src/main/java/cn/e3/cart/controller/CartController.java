package cn.e3.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 购物车管理
 * @author Administrator
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.CallableMethodReturnValueHandler;

import cn.e3.cart.service.CartService;
import cn.e3.common.utils.CookieUtils;
import cn.e3.common.utils.E3Result;
import cn.e3.common.utils.JsonUtils;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbUser;
import cn.e3.service.TbItemService;
@Controller
public class CartController {
	
	@Value("${CART_COOKIE}")
	private String CART_COOKIE;
	
	@Value("${CART_EXPIR}")
	private Integer CART_EXPIR;
	
	@Autowired
	private TbItemService tbItemService;
	
	@Autowired
	private CartService cartService;
	
	/**
	 * 添加商品到购物车
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId,Integer num,
			HttpServletRequest request,HttpServletResponse response){
		TbUser user = (TbUser) request.getAttribute("tbUser");
		//判断是否登录
		if(user!=null){
			cartService.addCart(itemId, num, user.getId());
			return "cartSuccess";
		}
		
		//获取cookie中的购物列表信息
		List<TbItem> tbItems = getTbItemsFormCookie(request);
		//判断商品是否存在
		boolean flag = false;
		for(TbItem tbItem:tbItems){
			if(tbItem.getId()==itemId.longValue()){
				tbItem.setNum(tbItem.getNum()+num);
				flag = true;
				break;
			}
		}
		//商品不存在
		if(!flag){
			TbItem tbItem = tbItemService.findTbItemById(itemId);
			tbItem.setNum(num);
			//取一张图片
			String image = tbItem.getImage();
			if(StringUtils.isNoneBlank(image)){
				tbItem.setImage(image.split(",")[0]);
			}
			
			tbItems.add(tbItem);
		}
		
		//将购物车写入cookie
		CookieUtils.setCookie(request, response,CART_COOKIE,JsonUtils.objectToJson(tbItems),CART_EXPIR,true);
		return "cartSuccess";
	}
	/**
	 * 显示购物车信息
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String showCart(HttpServletRequest request,HttpServletResponse response){
		List<TbItem> tbItems = getTbItemsFormCookie(request);
		//判断是否登录
		TbUser tbUser = (TbUser) request.getAttribute("tbUser");
		if(tbUser!=null){
			//合并cookie中的购物车信息
			cartService.merageCart(tbUser.getId(), tbItems);
			//清空cookie中的信息
			CookieUtils.deleteCookie(request, response, CART_COOKIE);
			tbItems = cartService.getItemList(tbUser.getId());
		}

		request.setAttribute("cartList", tbItems);
		return "cart";
	}
	
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateTbItemNum(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
		//判断是否登录
		TbUser tbUser = (TbUser) request.getAttribute("tbUser");
		if(tbUser!=null){
			cartService.updateCartNum(tbUser.getId(), itemId, num);
			return E3Result.ok();
		}
		
		//获取cookie中的商品列表信息
		List<TbItem> tbItems = getTbItemsFormCookie(request);
		//修改商品数量
		for(TbItem tbItem :tbItems){
			if(tbItem.getId()==itemId.longValue()){
				tbItem.setNum(num);
				break;
			}
		}
		
		//写入cookie
		CookieUtils.setCookie(request, response,CART_COOKIE,JsonUtils.objectToJson(tbItems),CART_EXPIR,true);
		return E3Result.ok();
	}
	
	/**
	 * 删除购物车中的商品
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteTbItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
		//判断是否登录
		TbUser tbUser = (TbUser) request.getAttribute("tbUser");
		if(tbUser!=null){
			cartService.deleteCartItem(tbUser.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		
		List<TbItem> tbItems = getTbItemsFormCookie(request);
		//遍历列表找到对应的商品
		for (TbItem tbItem : tbItems) {
			if (tbItem.getId() == itemId.longValue()) {
				// 4、删除商品。
				tbItems.remove(tbItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response,CART_COOKIE,JsonUtils.objectToJson(tbItems),CART_EXPIR,true);
		//返回购物车视图
		return "redirect:/cart/cart.html";
	}
	/**
	 * 从cookie中获取商品列表信息
	 * @param request
	 * @return
	 */
	public List<TbItem> getTbItemsFormCookie(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request, CART_COOKIE,true);
		//判断购物车是否为空
		if(StringUtils.isBlank(json)){
			return new ArrayList();
		}
		return JsonUtils.jsonToList(json, TbItem.class);
	}
	
	
}
