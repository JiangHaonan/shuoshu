 package ss.web.boss.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ss.common.constant.Constant;
import ss.common.util.PrincipalUtils;
import ss.common.util.SessionUtils;
import ss.common.util.StringUtils;
import ss.facade.user.entity.User;


/**
 * 表单验证（包含验证码）过滤类
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(FormAuthenticationFilter.class);

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = StringUtils.isNull(getPassword(request));
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		//说书暂时不使用验证码
		String captcha = "";
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
 
	
	public String getMessageParam() {
		return messageParam;
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
		//在这个地方把用户存到session中
		User user = (User) PrincipalUtils.getPrincipal();
		SessionUtils.setSessionAttr(Constant.SESSION_USER, user);
		WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
	}

	/**
	 * 登录失败调用事件
	 * 如果验证失败，会调用这个方法来处理异常，然后在controller中取出就可以了
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		//经过前面的验证，后面都使用这个提示来提示登录失败
		String message =  "用户或密码错误, 登录失败请重试！";
		//这个getFailureKeyAttribute()其实就是取到异常的名称，其实就是""shiroLoginFailure
        request.setAttribute(getFailureKeyAttribute(), e.getClass().getName());
        request.setAttribute(getMessageParam(), message);
        return true;
	}
	
}