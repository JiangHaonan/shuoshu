package ss.facade.user.service;

/**
 * @author mutou
 * @date 2017年7月13日
 */
public class UserException extends BussinessException{
	
	private static final long serialVersionUID = 1L;

	public UserException(final String message, final int type, final int errorCode) {
		super(message,type,errorCode);
	}

	public UserException(final String message, final int type) {
		super(message,type);
	} 
}
