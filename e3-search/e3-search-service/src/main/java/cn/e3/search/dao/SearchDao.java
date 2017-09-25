package cn.e3.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3.common.pojo.EasyUIVo;
import cn.e3.common.pojo.SearchItem;
import cn.e3.common.pojo.SearchResult;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
/**
 * 商品搜索dao
 * @author Administrator
 *
 */
@Repository
public class SearchDao {
	@Autowired
	private SolrServer solrServer;
	
	/**
	 * 根据查询条件查询索引库
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public SearchResult getItemList(SolrQuery query) throws Exception{
		QueryResponse queryResponse = solrServer.query(query);
		//获取所有结果
		SolrDocumentList responseResult = queryResponse.getResults();
		//获取总记录数
		Long num = responseResult.getNumFound();
		//创建返回对象
		SearchResult result = new SearchResult();
		result.setRecordCount(num);
		List<SearchItem> searchItems = new ArrayList<SearchItem>();
		//取高亮
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		//封装数据
		for(SolrDocument doc:responseResult){
			SearchItem item = new SearchItem();
			item.setId((String) doc.get("id"));
			item.setCategory_name((String) doc.get("item_category_name"));
			item.setImage((String) doc.get("item_image"));
			item.setPrice((long) doc.get("item_price"));
			item.setSell_point((String) doc.get("item_sell_point"));
			String title;
			List<String> list = highlighting.get(doc.get(("id"))).get("item_title");
			if(list!=null&&list.size()>0){
				title = list.get(0);
			}else{
				title = (String) doc.get("item_title");
			}
			item.setTitle(title);
			searchItems.add(item);
		}
		
		result.setItemList(searchItems);
		return result;
	}
}
