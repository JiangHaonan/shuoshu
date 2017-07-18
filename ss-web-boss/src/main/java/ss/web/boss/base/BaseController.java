package ss.web.boss.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ss.common.constant.Constant;
import ss.common.util.SessionUtils;
import ss.facade.user.entity.User;

/**
 * Created by mutou on 2017/5/23.
 * 日后可以修改成shiro管理session
 */
public class BaseController {
	
	public static User getSessionUser(){
		return (User) SessionUtils.getSessionAttr(Constant.SESSION_USER);
	}

	public static HttpSession getSession() { 
		HttpSession session = null; 
		try { 
		    session = getRequest().getSession(); 
		} catch (Exception e) {
			
		} 
		return session; 
	} 	

	public static HttpServletRequest getRequest() { 
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); 
		return attrs.getRequest(); 
	}
}
