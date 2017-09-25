package cn.e3.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 测试spring整合activemq
 * @author Administrator
 *
 */
public class TestSpring_activemq {
/*	
	@Test
	public void testProducter(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/Springmvc-activemq.xml");
		//获取jmstemplate对象
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		//获取destination
		Destination destination = (Destination) context.getBean("queueDestination");
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage("hello spring");
				return message;
			}
		});
	}*/
}
