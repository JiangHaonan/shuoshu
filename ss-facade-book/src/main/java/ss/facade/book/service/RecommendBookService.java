package ss.facade.book.service;

import ss.common.core.service.BaseService;
import ss.common.orm.Page;
import ss.facade.book.entity.Book;
import ss.facade.book.entity.RecommendBook;
import ss.facade.book.entity.model.BookModel;
import ss.facade.user.entity.User;

public interface RecommendBookService extends BaseService<RecommendBook>{
	
	void addRecommendBook(Book book, User reUser);
	
	public Page<Book> memberRecommendList(BookModel book);
}
