package cn.e3.content.service;

import java.util.List;

import cn.e3.common.pojo.EasyUITree;
import cn.e3.common.utils.E3Result;

/**
 * 内容分类管理
 * @author Administrator
 *
 */
public interface TbContentCategoryService {
	/**
	 * 根据父id获取子节点
	 * @param parentId
	 * @return
	 */
	public List<EasyUITree> getTbContentCategoryList(long parentId);
	
	/**
	 * 根据父节点添加子节点
	 * @return
	 */
	public E3Result addTbContentCategory(long parentId,String name);
	
	/**
	 * 根据id修改节点
	 * @param id
	 * @param name
	 * @return
	 */
	public E3Result updateContentCategory(long id,String name);
	
	/**
	 * 根据id删除节点
	 * @param id
	 * @return
	 */
	public E3Result deleteContentCategory(long id);
}
