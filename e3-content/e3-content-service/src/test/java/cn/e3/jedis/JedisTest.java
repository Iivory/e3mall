package cn.e3.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3.common.jeids.JedisClient;

public class JedisTest {
	
	/*@Test
	public void testJedisPool(){
		ApplicationContext bean = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
		JedisClient client = bean.getBean(JedisClient.class);
		client.set("key1","12354");
		String str = client.get("key1");
		System.out.println(str);
	}
	*/
/*	@Test
	public void testJedisCluster(){
		ApplicationContext bean = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
		JedisClient client = bean.getBean(JedisClient.class);
		client.set("key3","12354");
		String str = client.get("key3");
		System.out.println(str);
	}*/
}
