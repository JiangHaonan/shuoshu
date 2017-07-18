package ss.service.user.impl;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import ss.common.Result;
import ss.common.core.service.BaseServiceImpl;
import ss.common.util.DateUtils;
import ss.facade.user.entity.User;
import ss.facade.user.entity.model.UserModel;
import ss.facade.user.service.UserService;
import ss.service.user.mapper.UserMapper;

/**
 * @author mutou
 * @date 2017年7月13日
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService{
	@Override
	public Result doRegister(UserModel model) {
		Result result = new Result();
		model.validPassword();
		User user = dao.getByUsername(model.getUsername());
		if(user != null){
			result.setMsg("该用户名已存在");
			return result;
		}
		User insertUser = new User();
		insertUser.setUsername(model.getUsername());
		String newPassword = new SimpleHash("md5",model.getPassword()).toString();
		insertUser.setPassword(newPassword);
		insertUser.setAddTime(DateUtils.getNow());
		insert(insertUser);
		result.setCode(100);
		result.setMsg("注册成功");
		return result;
	}

	@Override
	public User getUserByUsername(String username) {
		return dao.getByUsername(username);
	}

	@Override
	public User getUserByEmail(String email) {
		return dao.getByEmail(email);
	}

}
