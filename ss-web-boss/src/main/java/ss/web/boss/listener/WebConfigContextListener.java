package ss.web.boss.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import ss.common.util.PropertiesUtils;
import ss.web.boss.global.Global;

/**
 * @author mutou
 * @date 2017年7月17日
 */
public class WebConfigContextListener implements ServletContextListener, HttpSessionAttributeListener {
	
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	public void contextInitialized(ServletContextEvent event) {
		Global.setConfig("storage_url", PropertiesUtils.getValue("storage_url"));
	}

	
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
