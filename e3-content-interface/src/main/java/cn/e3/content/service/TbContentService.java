package cn.e3.content.service;

import java.util.List;

import cn.e3.common.pojo.EasyUIVo;
import cn.e3.common.utils.E3Result;
import cn.e3.pojo.TbContent;

/**
 * 内容管理
 * @author Administrator
 *
 */
public interface TbContentService {
	/**
	 * 获取内容
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIVo<TbContent> getContentList(int page,int rows);
	
	/**
	 * 添加内容
	 * @param tbContent
	 * @return
	 */
	public E3Result addTbContent(TbContent tbContent);
	
	/**
	 * 修改内容
	 * @param tbContent
	 * @return
	 */
	public E3Result updateTbContent(TbContent tbContent);
	
	/**
	 * 删除内容
	 * @param ids
	 * @return
	 */
	public E3Result deleteTbContent(String ids);
	
	/**
	 * 根据分类id获取内容
	 * @param cONTENT_CATEGORY_ID
	 * @return
	 */
	public List<TbContent> getContentListByCid(Long CONTENT_CATEGORY_ID);
}
