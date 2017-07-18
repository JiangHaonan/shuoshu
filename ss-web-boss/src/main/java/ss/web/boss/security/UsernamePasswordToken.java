 package ss.web.boss.security;

/**
 * 用户和密码（包含验证码）令牌类
 * @version 2013-5-19
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
    /**
     * 验证码
     */
	private String captcha;
	
	public UsernamePasswordToken() {
		super();
	}
   
	public UsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
}