package cn.e3.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**
 * 测试消息队列
 * @author Administrator
 *
 */
public class TestActivemq {
	/*@Test
	public void testQueueProducter() throws Exception{
		//创建工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		//获取连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//创建session 1:是否开启事务 2:应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建destination
		Queue queue = session.createQueue("test_queue");
		//创建提供者
		MessageProducer producter = session.createProducer(queue);
		//创建一个信息
		TextMessage message = session.createTextMessage("hello wolrd");
		//发送信息
		producter.send(message);
		//关闭资源
		producter.close();
		session.close();
		connection.close();
	}
	@Test
	public void testQueueConsumer() throws Exception{
		//创建工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		//获取连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//创建session 1:是否开启事务 2:应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建destination
		Queue queue = session.createQueue("test_queue");
		//创建提供者
		MessageConsumer consumer = session.createConsumer(queue);
		//接受信息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//停止
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testTopicProducter() throws Exception{
		//创建工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		//获取连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//创建session 1:是否开启事务 2:应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建destination
		Topic topic = session.createTopic("test_topic");
		//创建提供者
		MessageProducer producter = session.createProducer(topic);
		//创建一个信息
		TextMessage message = session.createTextMessage("hello wolrd");
		//发送信息
		producter.send(message);
		//关闭资源
		producter.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testTopicConsumer() throws Exception{
		//创建工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		//获取连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//创建session 1:是否开启事务 2:应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建destination
		Topic topic = session.createTopic("test_topic");
		//创建提供者
		MessageConsumer consumer = session.createConsumer(topic);
		//接受信息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//停止
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	*/
}
