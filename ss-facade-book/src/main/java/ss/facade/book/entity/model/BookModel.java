package ss.facade.book.entity.model;

import org.apache.commons.lang3.ArrayUtils;

import ss.common.util.StringUtils;
import ss.facade.book.entity.Book;
import ss.facade.book.service.BookException;
import ss.facade.user.service.BussinessException;

public class BookModel extends Book{

	
	private static final long serialVersionUID = 1L;
	
	public final static String APITYPE = "0";
	
	public final static String FASTDFSTYPE = "1";
	
	private String userUuid;
	
	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public static String pasrseAuthor(String authors){
		if(StringUtils.isBlank(authors)){
			return "";
		}
		String[] aus = authors.split(",");
		StringBuffer buffer = new StringBuffer("[");
		if(ArrayUtils.isEmpty(aus)){
			return "";
		}
		for(String author : aus){
			buffer.append("\"" + author +"\",");
		}
		buffer.deleteCharAt(buffer.length()-1);
		buffer.append("]");
		return buffer.toString();
	}
	
	public static void checkBook(Book book){
		if(StringUtils.isBlank(book.getTitle())){
			throw new BookException("书名不能为空", BussinessException.TYPE_JSON);
		}
	}
}
