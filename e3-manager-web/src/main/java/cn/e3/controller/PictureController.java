package cn.e3.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3.common.utils.FastDFSClient;
import cn.e3.common.utils.JsonUtils;
/**
 * 图片上传管理
 * @author Administrator
 *
 */
@Controller
public class PictureController {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String fileUpload(MultipartFile uploadFile){
		
		try{
			//获取文件名
			String fileName = uploadFile.getOriginalFilename();
			//获取扩展名
			String exName = fileName.substring(fileName.lastIndexOf(".")+1);
			//使用文件上传工具类
			FastDFSClient client = new FastDFSClient("classpath:conf/client.conf");
			String path = client.uploadFile(uploadFile.getBytes(),exName);
			//拼接url
			String url = IMAGE_SERVER_URL+path;
			//创建返回json
			Map map = new HashMap();
			map.put("error", 0);
			map.put("url", url);
			return JsonUtils.objectToJson(map);
		}catch(Exception e){
			e.printStackTrace();
			Map map = new HashMap();
			map.put("error", 1);
			map.put("message", "图片上传失败");
			return JsonUtils.objectToJson(map);
		}
	}
}
