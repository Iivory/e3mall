package cn.e3.search.mapper;

import java.util.List;

import cn.e3.common.pojo.SearchItem;

public interface ItemMapper {
	List<SearchItem> getItemList();
	public SearchItem getSearchItemById(Long id);
}
