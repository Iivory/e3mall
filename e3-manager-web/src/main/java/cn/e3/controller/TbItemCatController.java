package cn.e3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.common.pojo.EasyUITree;
import cn.e3.service.TbItemCatService;

/**
 * 商品类目管理
 * @author Administrator
 *
 */
@Controller
public class TbItemCatController {
	
	@Autowired
	private TbItemCatService tbItemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITree> getTbItemCatLIst(@RequestParam(value="id",defaultValue="0") long parentId){
		List<EasyUITree> tree = tbItemCatService.getTbItemCatListByParentId(parentId);
		return tree;
	}
}
