package cn.e3.search.service;

import cn.e3.common.pojo.SearchItem;
import cn.e3.common.utils.E3Result;
/**
 * solr索引维护
 * @author Administrator
 *
 */
public interface SearchItemService {
	/**
	 * 导入创建索引
	 * @return
	 */
	public E3Result importAllItems();


}
