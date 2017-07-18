package ss.facade.user.entity.model;


import java.util.Objects;

import ss.facade.user.entity.User;
import ss.facade.user.service.BussinessException;
import ss.facade.user.service.UserException;

/**
 * Created by mutou on 2017/5/23.
 */
public class UserModel extends User {

	private static final long serialVersionUID = 1L;
	/**
     * 重复密码
     */
    private String repassword;


    public void validLoginInfo() {

    }

    public void validRegister(){

    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
    
    public void validPassword(){
    	//简单的校验重复密码，抛出异常之后，由handle来处理
    	if(!Objects.equals(getPassword(), getRepassword())){
    		throw new UserException("两次输入的密码不一致",BussinessException.TYPE_JSON);
    	}
    	return;
    }
}
