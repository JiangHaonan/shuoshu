package ss.service.book.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ss.common.core.service.BaseServiceImpl;
import ss.common.orm.Page;
import ss.facade.book.entity.Book;
import ss.facade.book.entity.model.BookModel;
import ss.facade.book.service.BookService;
import ss.service.book.mapper.BookMapper;

@Service(value="bookService")
public class BookServiceImpl extends BaseServiceImpl<BookMapper, Book> implements BookService{

	public Page<Book> findPageData(BookModel book) {
		List<Book> list = dao.findPageData(book);
		book.getPage().setRows(list);
		return book.getPage();
	}

	public Page<Book> findRecommendList(BookModel book) {
		List<Book> list = dao.findRecommendList(book);
		book.getPage().setRows(list);
		return book.getPage();
	}

	public Book findByUuid(String uuid) {
		return dao.get(uuid);
	}

	
}
