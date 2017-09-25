package cn.e3.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.common.jeids.JedisClient;
import cn.e3.common.pojo.EasyUIVo;
import cn.e3.common.utils.E3Result;
import cn.e3.common.utils.JsonUtils;
import cn.e3.content.service.TbContentService;
import cn.e3.dao.TbContentMapper;
import cn.e3.pojo.TbContent;
import cn.e3.pojo.TbContentExample;
import cn.e3.pojo.TbContentExample.Criteria;

/**
 * 内容管理
 * @author Administrator
 *
 */
@Service
public class TbContentServiceImpl implements TbContentService {
	
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	@Override
	public EasyUIVo<TbContent> getContentList(int page, int rows) {
		//查询获取
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		List<TbContent> tbContents = tbContentMapper.selectByExampleWithBLOBs(example);
		
		//封装参数
		EasyUIVo<TbContent> easyUIVo = new EasyUIVo<TbContent>();
		PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);
		easyUIVo.setRows(tbContents);
		easyUIVo.setTotal(pageInfo.getTotal());
		return easyUIVo;
	}
	@Override
	public E3Result addTbContent(TbContent tbContent) {
		Date date = new Date();
		//补全属性
		tbContent.setUpdated(date);
		tbContent.setCreated(date);
		tbContentMapper.insert(tbContent);
		jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId()+"");
		return E3Result.ok();
	}
	@Override
	public E3Result updateTbContent(TbContent tbContent) {
		Date date = new Date();
		//补全属性
		tbContent.setUpdated(date);
		tbContentMapper.updateByPrimaryKey(tbContent);
		jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId()+"");
		return E3Result.ok();
	}
	@Override
	public E3Result deleteTbContent(String ids) {
		String[] str = ids.split(",");
		for(String id :str){
			tbContentMapper.deleteByPrimaryKey(Long.parseLong(id));
			
		}
		return E3Result.ok();
	}
	@Override
	public List<TbContent> getContentListByCid(Long CONTENT_CATEGORY_ID) {
		String json = null;
		//查询缓存
		try{
			json = jedisClient.hget(CONTENT_KEY, CONTENT_CATEGORY_ID+"");
			if(StringUtils.isNotBlank(json)){
				List<TbContent> thContents = JsonUtils.jsonToList(json,TbContent.class);
				return thContents;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(CONTENT_CATEGORY_ID);
		List<TbContent> tbContents = tbContentMapper.selectByExample(example);
		
		//存入缓存
		try{
			json = JsonUtils.objectToJson(tbContents);
			jedisClient.hset(CONTENT_KEY, CONTENT_CATEGORY_ID+"",json);
		}catch(Exception e){
			e.printStackTrace();
		}
		return tbContents;
	}
	
}
