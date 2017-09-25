package cn.e3.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3.cart.service.CartService;
import cn.e3.common.jeids.JedisClient;
import cn.e3.common.utils.E3Result;
import cn.e3.common.utils.JsonUtils;
import cn.e3.dao.TbItemMapper;
import cn.e3.pojo.TbItem;
/**
 * 获得购物车列表
 * @author Administrator
 *
 */
@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CART_REDIS_PRE}")
	private String CART_REDIS_PRE;
	
	@Autowired
	private TbItemMapper tbItemMapper;

	public List<TbItem> getItemList(long userID) {
		List<String> json = jedisClient.hvals(CART_REDIS_PRE+":"+userID);
		List<TbItem> tbItems = new ArrayList<TbItem>();
		
		for(String str:json){
			TbItem tbItem = JsonUtils.jsonToPojo(str, TbItem.class);
			tbItems.add(tbItem);
		}
		return tbItems;
	}
	@Override
	public E3Result addCart(long itemID, Integer num, long userID) {
		//判断原有商品是否存在
		Boolean hexists = jedisClient.hexists(CART_REDIS_PRE+":"+userID, itemID+"");
		//存在
		if(hexists){
			String json = jedisClient.hget(CART_REDIS_PRE+":"+userID, itemID+"");
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			tbItem.setNum(num+tbItem.getNum());
			jedisClient.hset(CART_REDIS_PRE+":"+userID, itemID+"", JsonUtils.objectToJson(tbItem));
			return E3Result.ok();
		}
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemID);
		//设置属性
		tbItem.setNum(num);
		String images = tbItem.getImage();
		if(StringUtils.isNoneBlank(images)){
			tbItem.setImage(images.split(",")[0]);
		}
		jedisClient.hset(CART_REDIS_PRE+":"+userID, itemID+"", JsonUtils.objectToJson(tbItem));
		return E3Result.ok();
	}
	
	public E3Result merageCart(long userID,List<TbItem> tbItems){
		// TODO Auto-generated method stub
		for(TbItem tbItem:tbItems){
			addCart(tbItem.getId(), tbItem.getNum(), userID);
		}
		return E3Result.ok();
	}
	@Override
	public E3Result updateCartNum(long userID, long tbItemID,Integer num) {
		String json = jedisClient.hget(CART_REDIS_PRE+":"+userID,tbItemID+"");
		TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
		tbItem.setNum(num);
		jedisClient.hset(CART_REDIS_PRE+":"+userID, tbItemID+"", JsonUtils.objectToJson(tbItem));
		return E3Result.ok();
	}
	@Override
	public E3Result deleteCartItem(long userID, long tbItemID) {
		jedisClient.hdel(CART_REDIS_PRE+":"+userID, tbItemID+"");
		return E3Result.ok();
	}
	@Override
	public E3Result clearCartItem(Long id) {
		jedisClient.del(CART_REDIS_PRE+":"+id);
		return E3Result.ok();
		
	}

}
