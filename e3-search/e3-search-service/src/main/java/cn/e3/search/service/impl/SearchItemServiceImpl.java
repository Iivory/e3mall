package cn.e3.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.common.pojo.SearchItem;
import cn.e3.common.utils.E3Result;
import cn.e3.search.mapper.ItemMapper;
import cn.e3.search.service.SearchItemService;
/**
 * solr索引创建实现
 * @author Administrator
 *
 */
@Service
public class SearchItemServiceImpl implements SearchItemService{
	
	@Autowired 
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	

	@Override
	public E3Result importAllItems() {
		try {
			//查询商品列表
			List<SearchItem> itemList = itemMapper.getItemList();
			
			//导入索引库
			for(SearchItem item :itemList){
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", item.getId());
				doc.addField("item_title", item.getTitle());
				doc.addField("item_sell_point", item.getSell_point());
				doc.addField("item_price", item.getPrice());
				doc.addField("item_image", item.getImage());
				doc.addField("item_category_name", item.getCategory_name());
				solrServer.add(doc);
			}
			solrServer.commit();
			return E3Result.ok();
			
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "商品导入失败");
		}
	}


	


}
