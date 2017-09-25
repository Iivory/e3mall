package cn.e3.fast;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3.common.utils.FastDFSClient;

public class TestFastDFS {
	
/*	@Test
	public void testUpload() throws FileNotFoundException, IOException, MyException{
//		1、加载配置文件，配置文件中的内容就是tracker服务的地址。

		ClientGlobal.init("E:\\Develope\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
//		2、创建一个TrackerClient对象。直接new一个。
		TrackerClient trackerClient = new TrackerClient();
//		3、使用TrackerClient对象创建连接，获得一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
//		4、创建一个StorageServer的引用，值为null
		StorageServer storageServer = null;
//		5、创建一个StorageClient对象，需要两个参数TrackerServer对象、StorageServer的引用
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//		6、使用StorageClient对象上传图片。
		String[] upload_file = storageClient.upload_file("C:\\Users\\Administrator\\Desktop\\57b12a31N8f4f75a3.jpg", "jpg", null);
//		7、返回数组。包含组名和图片的路径。
		for(String str:upload_file){
			System.out.println(str);
		}
	} 
	*/
/*	@Test
	public void testUtils() throws Exception{
		FastDFSClient client = new FastDFSClient("E:\\Develope\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
		String str = client.uploadFile("C:\\Users\\Administrator\\Desktop\\57b12a31N8f4f75a3.jpg");
		System.out.println(str);
	}*/
	
}
