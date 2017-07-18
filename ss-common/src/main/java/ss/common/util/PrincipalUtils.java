package ss.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrincipalUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrincipalUtils.class);
	
	/**
	 * get subject
	 * @return
	 */
	public static Subject getSubject(){
		//get Subject
		Subject subject = null;		
		try {
			subject = SecurityUtils.getSubject();
		} catch (org.apache.shiro.UnavailableSecurityManagerException e) {
			LOGGER.error("No SecurityManager accessible to the calling code", e);
		}		
		return subject;
	}
	
	/**
	 * 取得当前账号
	 * @return
	 */
	public static Object getPrincipal(){
		
		Subject subject = getSubject();
		if(subject == null){
			return null;
		}		
		
		//get PrincipalCollection
		PrincipalCollection principals = subject.getPrincipals();
		if(principals == null){
			return null;
		}
		
		return principals.getPrimaryPrincipal();
		
	}

}
