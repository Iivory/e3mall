package cn.e3.test.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {
	
/*	@Test
	public void addDocument() throws SolrServerException, IOException{
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.132:8080/solr");
		//创建文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("name", "jack");
		document.addField("id", "1");
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void testDelete() throws SolrServerException, IOException{
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.132:8080/solr");
		solrServer.deleteById("1");
		solrServer.commit();
	}*/
}
