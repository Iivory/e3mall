package cn.e3.controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.remoting.exchange.Request;

import cn.e3.common.pojo.EasyUIVo;
import cn.e3.common.utils.E3Result;
import cn.e3.common.utils.IDUtils;
import cn.e3.pojo.TbItem;
import cn.e3.service.TbItemService;
/**
 * 商品管理
 * @author Administrator
 *
 */
@Controller
public class TbItemController {
	
	@Autowired
	private TbItemService tbItemService;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getTbItemById(@PathVariable Long itemId){
		TbItem tbItem = tbItemService.findTbItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIVo<TbItem> getItemList(int page,int rows){
		EasyUIVo<TbItem> vo = tbItemService.getTbItemList(page, rows);
		return vo;
	}
	
	/**
	 * 添加商品
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addTbItem(TbItem tbItem,String desc){
		final long id = IDUtils.genItemId();
		tbItem.setId(id);
		E3Result result = tbItemService.addTbItem(tbItem, desc);
		//发送添加商品信息
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(id+"");
				return textMessage;
			}
		});
		return result;
	}
	
	/**
	 * 跳转修改页面
	 * @param path
	 * @return
	 */
	@RequestMapping("/rest/page/{path}")
	public String editTbItem(@PathVariable String path){
		return path;
	}
	/**
	 * 修改商品
	 * @param tbItem
	 * @return
	 */
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public E3Result updateItem(TbItem tbItem){
		E3Result result = tbItemService.updateTbItem(tbItem);
		return result;
	}
	/**
	 * 删除商品
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public E3Result deleteTbItem(String ids){
		E3Result result = tbItemService.deleteItems(ids);
		return result;
	}
	/**
	 * 商品上架
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public E3Result putAway(String ids){
		E3Result result = tbItemService.updateStatus(1,ids);
		return result;
	}
	/**
	 * 商品下架
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public E3Result soldOut(String ids){
		E3Result result = tbItemService.updateStatus(2,ids);
		return result;
	}
}
