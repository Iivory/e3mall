package cn.e3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.common.pojo.EasyUITree;
import cn.e3.common.utils.E3Result;
import cn.e3.content.service.TbContentCategoryService;
/**
 * 内容分类管理
 * @author Administrator
 *
 */
@Controller
public class TbContentCategoryController {
	
	@Autowired
	private TbContentCategoryService tbContentCategoryService;
	
	/**
	 * 获取所有内容分类
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITree> getTbContentCategoryList(@RequestParam(value="id",defaultValue="0") long parentId){
		List<EasyUITree> tree = tbContentCategoryService.getTbContentCategoryList(parentId);
		return tree;
	}
	/**
	 * 新增分类
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3Result addTbContentCategory(long parentId,String name){
		E3Result result = tbContentCategoryService.addTbContentCategory(parentId, name);
		return result;
	}
	
	/**
	 * 更新分类名
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateContentCategory(long id,String name){
		E3Result result = tbContentCategoryService.updateContentCategory(id, name);
		return result;
	}
	
	/**
	 * 根据id删除节点
	 * @param id
	 * @return
	 */
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public E3Result deleteContentCategory(long id){
		E3Result result = tbContentCategoryService.deleteContentCategory(id);
		return result;
	}
}
