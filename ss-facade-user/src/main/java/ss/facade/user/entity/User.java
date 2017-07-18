package ss.facade.user.entity;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import ss.common.core.entity.BaseEntity;

/**
 * 前台用户类
 * @author mutou
 * @date 2017年7月12日
 */
public class User extends BaseEntity<User>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空")
	@Pattern(regexp = "^(?![0-9]+$)[0-9A-Za-z]{6,20}$", message="用户名由6-20位字符组成，由英文字母、英文字母加数字组成")
	private String username;
	
	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空")
	@Pattern(regexp = "^[0-9]{6,14}$", message="密码必须由6-14位数字组成")
	private String password;
	
	/**
	 * email
	 */
	private String email;
	
	/**
	 * 手机号
	 */
	private String mobilePhone;
	
	/**
	 * 注册时间
	 */
	private Date addTime;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
