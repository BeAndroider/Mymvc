package base;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HandlerMapping map;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String uri = request.getRequestURI();
		System.out.println("uri:"+uri);
		String app = request.getContextPath();//获取应用名
		System.out.println("app:"+app);
		String path = uri.substring(app.length());
		
		Handler handler = map.getHandler(path);
		Method method = handler.getMethod();
		Object obj = handler.getObj();
		
		//返回一个jsp文件路径需要 “/WEB-INF/”+view名+".jsp"
		try {
			String viewName = (String) method.invoke(obj);
			String url = "/WEB-INF/"+viewName+".jsp";
			System.out.println(url);
			request.getRequestDispatcher(url).forward(request, response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void init() throws ServletException {
		
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read("F:\\Eclipse\\mymvc\\src\\main\\resources\\beans.xml");
			Element root = doc.getRootElement();
			List<Element> beans = root.elements();
			List<Object> objs = new ArrayList<>();
			for(Element e : beans) {
				String className = e.attributeValue("class");
				Class cls = Class.forName(className);
				Object obj = cls.newInstance();
				objs.add(obj);
			}
			
			//创建HandlerMapping用于管理请求路径和处理器的关系
			map = new HandlerMapping();
			map.process(objs);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("在初始化方法这里出错");
			e.printStackTrace();
		}
		
	}

}
