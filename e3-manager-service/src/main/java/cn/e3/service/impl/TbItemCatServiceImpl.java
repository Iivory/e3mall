package cn.e3.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.common.pojo.EasyUITree;
import cn.e3.dao.TbItemCatMapper;
import cn.e3.pojo.TbItemCat;
import cn.e3.pojo.TbItemCatExample;
import cn.e3.pojo.TbItemCatExample.Criteria;
/**
 * 商品类目管理
 * @author Administrator
 *
 */
@Service
public class TbItemCatServiceImpl implements cn.e3.service.TbItemCatService {
	
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Override
	public List<EasyUITree> getTbItemCatListByParentId(long parentId) {
		//查询类目
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		
		//封装格式
		List<EasyUITree> tbItemCatTrees = new ArrayList<EasyUITree>();
		for(TbItemCat tbItenCat:list){
			EasyUITree tbItenCatTree = new EasyUITree();
			tbItenCatTree.setId(tbItenCat.getId());
			tbItenCatTree.setText(tbItenCat.getName());
			tbItenCatTree.setState(tbItenCat.getIsParent()?"closed":"open");
			tbItemCatTrees.add(tbItenCatTree);
		}
		return tbItemCatTrees;
	}

}
