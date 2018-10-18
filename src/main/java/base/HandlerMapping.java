package base;
import java.lang.reflect.Method;
/**
 * 建立请求路径和处理器的关系
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anno.RequestMapping;

public class HandlerMapping {
	
	private Map<String,Handler> mapping = new HashMap<>();

	public void process(List<Object> objs) {
		for(Object obj : objs) {
			Class cls = obj.getClass();
			Method[] methods = cls.getDeclaredMethods();
			for(Method m : methods) {
				RequestMapping rm = m.getAnnotation(RequestMapping.class);
				if(rm != null) {
					String path = rm.value();
					System.out.println("HandlerMapping里面："+path);
					mapping.put(path, new Handler(m,obj));
				}
			}
		}
		
	}

	public Handler getHandler(String path) {
		return mapping.get(path);	
	}
}
