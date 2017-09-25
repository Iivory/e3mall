package cn.e3.item.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

public class TestFreemarker {
	@Test
	public void testHTMLGEN() throws Exception{
		/*第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		第二步：设置模板文件所在的路径。
		第三步：设置模板文件使用的字符集。一般就是utf-8.
		第四步：加载一个模板，创建一个模板对象。
		第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		第七步：调用模板对象的process方法输出文件。
		第八步：关闭流。*/
	/*	Configuration configuration = new Configuration(Configuration.getVersion());
		configuration.setDirectoryForTemplateLoading(new File("E:/Develope/e3-item-web/src/main/webapp/WEB-INF/ftl"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("hello.ftl");
		Map map = new HashMap();
		map.put("hello", "hello world");
		Writer out = new FileWriter(new File("c:/temp/hello.txt"));
		template.process(map, out);
		out.close();*/
	}
}
