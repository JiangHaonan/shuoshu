package ss.facade.book.service;

import ss.common.core.service.BaseService;
import ss.common.orm.Page;
import ss.facade.book.entity.Book;
import ss.facade.book.entity.model.BookModel;

public interface BookService extends BaseService<Book>{
	
	Page<Book> findPageData(final BookModel book);

	Page<Book> findRecommendList(BookModel book);

	Book findByUuid(String uuid);
}
