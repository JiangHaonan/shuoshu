package ss.facade.book.entity;

import ss.common.core.entity.BaseEntity;

public class RecommendBook extends BaseEntity<RecommendBook>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 书的uuid
	 */
	private String bookUuid;
	
	/**
	 * 推荐用户的uuid
	 */
	private String userUuid;

	public String getBookUuid() {
		return bookUuid;
	}

	public void setBookUuid(String bookUuid) {
		this.bookUuid = bookUuid;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	
	
}
