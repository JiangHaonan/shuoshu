package ss.common.util;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class SessionUtils {

	/**
	 * 获得session
	 * @param key
	 * @param value
	 */
	public static Session getSession(){
		Subject subject = PrincipalUtils.getSubject();
		if(subject == null){
			throw new RuntimeException("No Subject accessible to the calling code");
		}
		return subject.getSession(true);
	}
	
	/**
	 * 保存session相关属性
	 * @param key
	 * @param value
	 */
	public static void setSessionAttr(String key, Object value){
		Subject subject = PrincipalUtils.getSubject();
		if(subject == null){
			throw new RuntimeException("No Subject accessible to the calling code");
		}
		Session session = subject.getSession(true);
		session.setAttribute(key, value);
	}
	
	/**
	 * 取得session中对应 key的值
	 * @param key
	 * @return
	 */
	public static Object getSessionAttr(String key){
		Subject subject = PrincipalUtils.getSubject();
		if(subject == null){
			throw new RuntimeException("No Subject accessible to the calling code");
		}
		org.apache.shiro.session.Session session = subject.getSession();
		return session.getAttribute(key);
	}
	
	/**
	 * 移除session中对应 key的值
	 * @param key
	 * @return
	 */
	public static Object removeAttribute(String key){
		Subject subject = PrincipalUtils.getSubject();
		if(subject == null){
			throw new RuntimeException("No Subject accessible to the calling code");
		}
		org.apache.shiro.session.Session session = subject.getSession();
		return session.removeAttribute(key);
	}
	
}
