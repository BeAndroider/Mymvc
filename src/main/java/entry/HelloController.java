package entry;

import anno.RequestMapping;

public class HelloController {
	@RequestMapping("/hello.do")
	public String hello() {
		return "hello";
	}
}
