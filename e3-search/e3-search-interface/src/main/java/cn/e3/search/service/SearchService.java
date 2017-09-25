package cn.e3.search.service;

import cn.e3.common.pojo.SearchResult;
/**
 * 查询服务
 * @author Administrator
 *
 */
public interface SearchService {
	/**
	 * 通过关键字查询
	 * @param keyWord
	 * @param page
	 * @param rows
	 * @return
	 */
	SearchResult search(String keyWord,int page,int rows)throws Exception;
}
