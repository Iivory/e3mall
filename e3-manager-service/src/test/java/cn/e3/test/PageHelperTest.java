package cn.e3.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.dao.TbItemMapper;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemExample;
/**
 * 测试pageHelper
 * @author Administrator
 *
 */
public class PageHelperTest {
	
/*	@Test
	public void testPageHelper(){
		//初始化springmvc容器
		ApplicationContext bean = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		//获取到dao
		TbItemMapper tbItemMapper = bean.getBean(TbItemMapper.class);
		//执行sql语句之前设置分页信息
		PageHelper.startPage(1, 10);
		//查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//获取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		//总计录数
		//System.out.println(pageInfo.getTotal());
		//所有页数
		//System.out.println(pageInfo.getPages());
		//当前记录数
		//System.out.println(list.size());
		//当前页码
		//System.out.println(pageInfo.getPageNum());
	}*/
}
