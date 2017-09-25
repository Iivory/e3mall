package cn.e3.service;

import java.util.List;

import cn.e3.common.pojo.EasyUITree;

/**
 * 商品类目管理接口
 * @author Administrator
 *
 */
public interface TbItemCatService {
	/**
	 * 通过父类id找到所有的商品类目
	 * @param parentId
	 * @return
	 */
	public List<EasyUITree> getTbItemCatListByParentId(long parentId);
}
