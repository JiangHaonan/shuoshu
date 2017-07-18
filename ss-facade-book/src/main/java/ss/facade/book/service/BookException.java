package ss.facade.book.service;

/**
 * @author mutou
 * @date 2017年7月13日
 */
public class BookException extends BussinessException{
	
	private static final long serialVersionUID = 1L;

	public BookException(final String message, final int type, final int errorCode) {
		super(message,type,errorCode);
	}

	public BookException(final String message, final int type) {
		super(message,type);
	} 
}
