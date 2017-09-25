package cn.e3.cart.service;

import java.util.List;

import cn.e3.common.utils.E3Result;
import cn.e3.pojo.TbItem;

/**
 * 购物车管理
 * @author Administrator
 *
 */
public interface CartService {
	/**
	 * 获取购物车列表
	 * @param userID
	 * @return
	 */
	public List<TbItem> getItemList(long userID);
	
	/**
	 * 添加购物车列表
	 * @param itemID
	 * @param num
	 * @param userID
	 * @return
	 */
	public E3Result addCart(long itemID,Integer num,long userID);
	
	/**
	 * 合并cookie中的购物车与服务器中的购物车
	 * @param userId
	 * @param tbItems
	 * @return
	 */
	public E3Result merageCart(long userID,List<TbItem> tbItems);
	
	/**
	 * 更新购物车商品数量
	 * @param userID
	 * @param tbItemID
	 * @return
	 */
	public E3Result updateCartNum(long userID,long tbItemID,Integer num);
	/**
	 * 删除购物中的商品
	 * @param userID
	 * @param tbItemID
	 * @return
	 */
	public E3Result deleteCartItem(long userID,long tbItemID);
	/**
	 * 清空商品
	 * @param id
	 */
	public E3Result clearCartItem(Long id);
}
