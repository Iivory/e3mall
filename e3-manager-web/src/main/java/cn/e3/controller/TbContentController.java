package cn.e3.controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.common.pojo.EasyUIVo;
import cn.e3.common.utils.E3Result;
import cn.e3.content.service.TbContentService;
import cn.e3.pojo.TbContent;

/**
 * 内容控制
 * @author Administrator
 *
 */
@Controller
public class TbContentController {
	
	@Autowired
	private TbContentService TbContentService;

	
	/**
	 * 查询所有内容
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIVo<TbContent> getTbContentList(int page,int rows){
		EasyUIVo<TbContent> vo = TbContentService.getContentList(page, rows);
		return vo;
	}
	
	/**
	 * 添加内容
	 * @param tbContent
	 * @return
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addTbContent(TbContent tbContent){
		E3Result result = TbContentService.addTbContent(tbContent);
		return result;
	}
	
	/**
	 * 修改内容
	 * @param tbContent
	 * @return
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result editTbContent(TbContent tbContent){
		E3Result result = TbContentService.updateTbContent(tbContent);
		return result;
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result deleteTbContent(String ids){
		E3Result result = TbContentService.deleteTbContent(ids);
		return result;
	}
}
