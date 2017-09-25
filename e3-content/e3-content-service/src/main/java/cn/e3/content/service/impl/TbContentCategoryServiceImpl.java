package cn.e3.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.common.pojo.EasyUITree;
import cn.e3.common.utils.E3Result;
import cn.e3.content.service.TbContentCategoryService;
import cn.e3.dao.TbContentCategoryMapper;
import cn.e3.pojo.TbContentCategory;
import cn.e3.pojo.TbContentCategoryExample;
import cn.e3.pojo.TbContentCategoryExample.Criteria;
/**
 * 内容分类管理实现
 * @author Administrator
 *
 */
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<EasyUITree> getTbContentCategoryList(long parentId) {
		//获取分类
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		criteria.andStatusEqualTo(1);
		List<TbContentCategory> tbContentCategorys = tbContentCategoryMapper.selectByExample(example);
	
		//封装参数
		List<EasyUITree> easyUITrees = new ArrayList<EasyUITree>();
		for(TbContentCategory tbContentCategory:tbContentCategorys){
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(tbContentCategory.getId());
			easyUITree.setState(tbContentCategory.getIsParent()?"closed":"open");
			easyUITree.setText(tbContentCategory.getName());
			easyUITrees.add(easyUITree);
		}
		
		return easyUITrees;
	}
	@Override
	public E3Result addTbContentCategory(long parentId, String name) {
		//分装参数
		Date date = new Date();
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setCreated(date);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setName(name);
		tbContentCategory.setUpdated(date);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setStatus(1);
		//存入
		tbContentCategoryMapper.insert(tbContentCategory);
		//判断父节点是否true
		TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()){
			parentNode.setIsParent(true);;
			tbContentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		
		
		return E3Result.ok(tbContentCategory);
	}
	@Override
	public E3Result updateContentCategory(long id, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setName(name);
		tbContentCategory.setId(id);
		tbContentCategory.setUpdated(new Date());
		tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
		return E3Result.ok();
	}
	@Override
	public E3Result deleteContentCategory(long id) {
		
		TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		//判读删除的是否是父节点
		if(tbContentCategory.getIsParent()){
			//是父节点,先删除子节点,在删除父节点
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(id);
			List<TbContentCategory> tbContentCategorys = tbContentCategoryMapper.selectByExample(example);
			for(TbContentCategory tbContentCategory2 :tbContentCategorys){
				tbContentCategory2.setStatus(2);
				tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory2);
			}
		}
		tbContentCategory.setStatus(2);
		tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);

		return E3Result.ok();
	}

}
