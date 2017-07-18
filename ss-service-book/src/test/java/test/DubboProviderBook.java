package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 本地启动dubbo，参考wsc老师的代码
 * @author mutou
 * @date 2017年7月12日
 */
public class DubboProviderBook {
	
	private static final Log log = LogFactory.getLog(DubboProviderBook.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
		} catch (Exception e) {
			log.error("== DubboProvider context start error:",e);
		}
		synchronized (DubboProviderBook.class) {
			while (true) {
				try {
					DubboProviderBook.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:",e);
				}
			}
		}
	}
    
}