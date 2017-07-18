package ss.facade.book.service;


/**
 * 这里代码和ss-facade-user中的BussinessException冗余了，但目前暂未想到解决方法
 * @author mutou
 * @date 2017年7月13日
 */
public class BussinessException extends RuntimeException {
	private static final long serialVersionUID = 538922474277376456L;
	/**
	 * 返回Result
	 */
	public static final int TYPE_JSON = 1;
	/**
	 * 关闭窗
	 */
	public static final int TYPE_CLOSE = 2;
	/**
	 * 跳转url
	 */
	private String url;
	/**
	 * 返回错误信息，提示形式
	 * 
	 */
	private int type;
	
	/**
	 * 错误码
	 */
	private int errorCode;


	/**
	 * 默认构造方法
	 */
	public BussinessException() {
		super();
	}

	public BussinessException(final String message) {
		super(message);
	} 
	
	/**
	 * 含错误信息的构造方法
	 * 
	 * @param msg 错误信息
	 * @param type 指定返回错误展示方式
	 */
	public BussinessException(final String message, final int type) {
		super(message);
		this.type = type;
	}

	/**
	 * 含错误信息的构造方法
	 * 
	 * @param msg 错误信息
	 * @param type 指定返回错误展示方式
	 * @param errorCode 错误码
	 */
	public BussinessException(final String message, final int type, final int errorCode) {
		super(message);
		this.type = type;
		this.errorCode = errorCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
