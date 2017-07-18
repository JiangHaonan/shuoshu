package ss.facade.user.service;

import ss.common.Result;
import ss.facade.user.entity.User;
import ss.facade.user.entity.model.UserModel;

/**
 * Created by mutou on 2017/5/25.
 */
public interface UserService {
	
	/**
	 * 注册用户
	 * @param model
	 * @return
	 */
    public Result doRegister(UserModel model);
    
    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    public User getUserByUsername(String username);
    
    /**
     * 通过email获取用户
     * @param email
     * @return
     */
    public User getUserByEmail(String email);
}
