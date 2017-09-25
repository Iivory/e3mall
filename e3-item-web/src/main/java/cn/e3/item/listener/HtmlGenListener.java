package cn.e3.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.command.ActiveMQDestination;

import org.apache.activemq.memory.list.MessageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3.item.pojo.Item;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.service.TbItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 生成静态页面
 * @author Administrator
 *
 */

public class HtmlGenListener implements MessageListener {
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private TbItemService tbItemService;
	
	@Value("${HTML_GEN_PATH}")
	private String HTML_GEN_PATH;

	@Override
	public void onMessage(Message message) {
		
		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			Long id = new Long(text);
			//取出数据
			TbItemDesc itemDesc = tbItemService.getTbItemDescById(id);
			TbItem tbItem = tbItemService.findTbItemById(id);
			Item item = new Item(tbItem);
			
			//封装数据
			Map data = new HashMap();
			data.put("item", item);
			data.put("itemDesc",itemDesc);
			
			//生成静态页面
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			Writer out = new FileWriter(new File(HTML_GEN_PATH+id+".html"));
			
			template.process(data, out);
			
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
