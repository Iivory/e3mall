package cn.e3.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3.content.service.TbContentService;
import cn.e3.pojo.TbContent;
/**
 * 首页控制
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	@Value("${CONTENT_CATEGORY_ID}")
	private Long CONTENT_CATEGORY_ID;
	
	@Autowired
	private TbContentService tbContentService;
	
	@RequestMapping("index")
	public String showIndex(Model model){
		List<TbContent> tbContents = tbContentService.getContentListByCid(CONTENT_CATEGORY_ID);
		model.addAttribute("ad1List", tbContents);
		return "index";
	}
}
