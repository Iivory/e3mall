package cn.e3.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.common.jeids.JedisClient;
import cn.e3.common.pojo.EasyUIVo;
import cn.e3.common.utils.E3Result;
import cn.e3.common.utils.IDUtils;
import cn.e3.common.utils.JsonUtils;
import cn.e3.dao.TbItemDescMapper;
import cn.e3.dao.TbItemMapper;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.pojo.TbItemExample;
import cn.e3.service.TbItemService;
/**
 * 商品管理
 * @author Administrator
 *
 */
@Service
public class TbItemServiceImpl implements TbItemService {
	
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ITEM_INFO_PRE}")
	private String ITEM_INFO_PRE;
	
	@Value("${ITEM_EXPIRE}")
	private Integer ITEM_EXPIRE;
	
	@Override
	public TbItem findTbItemById(Long id) {
		//查询缓存
		try{
			String json = jedisClient.get(ITEM_INFO_PRE+":"+id+":BASE");
			//判断是否缓存
			if(StringUtils.isNotBlank(json)){
				//转为java
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		
		//加入缓存
		try{
			if(tbItem!=null){
				String json = JsonUtils.objectToJson(tbItem);
				jedisClient.set(ITEM_INFO_PRE+":"+id+":BASE", json);
				//设置过期时间
				jedisClient.expire(ITEM_INFO_PRE+":"+id+":BASE", ITEM_EXPIRE);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tbItem;
	}

	@Override
	public EasyUIVo<TbItem> getTbItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample tbItemExample = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);
		
		EasyUIVo<TbItem> vo = new EasyUIVo<TbItem>();vo.setRows(list);
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		vo.setTotal(pageInfo.getTotal());
		return vo;
	}

	@Override
	public E3Result addTbItem(TbItem tbItem, String desc) {
		//补全tbItem的属性

		Date date = new Date();
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		//商品状态 -1正常 -2下架 -3删除
		tbItem.setStatus((byte)1);
		
		//补全商品描述信息
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setCreated(date);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setItemId(tbItem.getId());
		tbItemDesc.setUpdated(date);
		
		//添加
		tbItemMapper.insert(tbItem);
		tbItemDescMapper.insert(tbItemDesc);
		return E3Result.ok();
	}

	@Override
	public E3Result updateTbItem(TbItem tbItem) {
		TbItem oldTbItem = findTbItemById(tbItem.getId());
		Date date = new Date();
		tbItem.setUpdated(date);
		//商品状态 -1正常 -2下架 -3删除
		tbItem.setStatus(oldTbItem.getStatus());
		tbItem.setCreated(oldTbItem.getCreated());;
		tbItemMapper.updateByPrimaryKey(tbItem);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteItems(String ids) {
		String[] strs = ids.split(",");
		for(String id:strs){
			tbItemMapper.updateStatusByPrimaryKey((byte)3, Long.parseLong(id));
		}
		return E3Result.ok();
	}

	@Override
	public E3Result updateStatus(int i, String ids) {
		String[] strs = ids.split(",");
		for(String id:strs){
			tbItemMapper.updateStatusByPrimaryKey((byte)i,  Long.parseLong(id));
		}
		return E3Result.ok();
	}

	@Override
	public TbItemDesc getTbItemDescById(Long id) {
		//查询缓存
		try{
			String json = jedisClient.get(ITEM_INFO_PRE+":"+id+":DESC");
			//判断是否缓存
			if(StringUtils.isNotBlank(json)){
				//转为java
				TbItemDesc TbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return TbItemDesc;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		
		//加入缓存
		try{
			if(tbItemDesc!=null){
				String json = JsonUtils.objectToJson(tbItemDesc);
				jedisClient.set(ITEM_INFO_PRE+":"+id+":DESC", json);
				//设置过期时间
				jedisClient.expire(ITEM_INFO_PRE+":"+id+":DESC", ITEM_EXPIRE);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tbItemDesc;
	}

}
