package ss.web.boss.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ss.facade.user.entity.User;
import ss.facade.user.service.UserService;

/**
 * @author mutou
 * @date 2017年7月13日
 */
@Service
public class ShuoshuAuthorizingRealm extends AuthorizingRealm {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShuoshuAuthorizingRealm.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		   UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		LOGGER.debug("login submit  username: {}" ,token.getUsername());
	    
	    //验证码校验
	    //TODO
	    //账户校验
	    User user = userService.getUserByUsername(token.getUsername());
	    if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

	    //不使用salt和加密次数，与注册账号时候对密码的加密保持一致
        return new SimpleAuthenticationInfo(
        		user, user.getPassword(), getName());
	}
	
	@Override
	public String getName() {
		return "shuoshuAuthorizingRealm";
	}

	/**
	 * 前台暂时用不到授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
