package cn.e3.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3.item.pojo.Item;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.service.TbItemService;
/**
 * 商品详情页
 * @author Administrator
 *
 */
@Controller
public class ItemController {
	
	@Autowired
	private TbItemService tbItemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable long itemId,Model model){
		TbItem tbItem = tbItemService.findTbItemById(itemId);
		Item item = new Item(tbItem);
		TbItemDesc tbItemDesc = tbItemService.getTbItemDescById(itemId);
		
		model.addAttribute("itemDesc", tbItemDesc);
		model.addAttribute("item", item);
		
		return "item";
	}
}
