package ss.service.user.mapper;

import ss.common.core.mapper.BaseMapper;
import ss.facade.user.entity.User;

public interface UserMapper extends BaseMapper<User>{
	
	/**
	 * 通过用户名查找用户
	 * @param userName
	 * @return
	 */
	User getByUsername(String userName);

	/**
	 * 通过email查找用户
	 * @param email
	 * @return
	 */
	User getByEmail(String email);
}
