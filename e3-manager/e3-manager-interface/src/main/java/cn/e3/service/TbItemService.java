package cn.e3.service;

import cn.e3.common.pojo.EasyUIVo;
import cn.e3.common.utils.E3Result;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;

/*
 * 商品管理接口
 */
public interface TbItemService {
	/**
	 * 通过商品id获取商品信息
	 * @param id
	 * @return
	 */
	public TbItem findTbItemById(Long id);
	
	/**
	 * 分页查询商品
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIVo<TbItem> getTbItemList(int page,int rows);
	
	/**
	 * 添加商品
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	public E3Result addTbItem(TbItem tbItem,String desc);
	
	/**
	 * 通过id修改商品信息
	 * @param id
	 * @return
	 */
	public E3Result updateTbItem(TbItem tbItem);
	/**
	 * 根据商品id删除商品
	 * @param ids
	 * @return
	 */
	public E3Result deleteItems(String ids);
	
	/**
	 * 根据商品id修改状态
	 * @param i
	 * @param ids
	 * @return
	 */
	public E3Result updateStatus(int i, String ids);
	/**
	 * 根据商品id获取商品描述
	 * @param id
	 * @return
	 */
	public TbItemDesc getTbItemDescById(Long id);
}
