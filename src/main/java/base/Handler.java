package base;

import java.lang.reflect.Method;

/**
 * 管理处理器的方法和
 * @author lenovo
 *
 */
public class Handler {
	private Method method;
	private Object obj;
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Handler(Method method, Object obj) {
		super();
		this.method = method;
		this.obj = obj;
	}
	
	
	
}
