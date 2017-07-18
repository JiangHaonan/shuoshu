package ss.service.book.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ss.common.core.service.BaseServiceImpl;
import ss.common.orm.Page;
import ss.facade.book.entity.Book;
import ss.facade.book.entity.RecommendBook;
import ss.facade.book.entity.model.BookModel;
import ss.facade.book.service.BookService;
import ss.facade.book.service.RecommendBookService;
import ss.facade.user.entity.User;
import ss.service.book.mapper.RecommendBookMapper;

@Service(value="recommendBookService")
public class RecommendBookServiceImpl extends BaseServiceImpl<RecommendBookMapper, RecommendBook> implements RecommendBookService{

	@Autowired
	public BookService bookService;
	
	@Transactional
	public void addRecommendBook(Book book, User reUser) {
		BookModel.checkBook(book);
		book.setAuthor(BookModel.pasrseAuthor(book.getAuthor()));
		book.preInsert();
		bookService.insert(book);
		
		RecommendBook reBook = new RecommendBook();
		reBook.setBookUuid(book.getUuid());
		reBook.setUserUuid(reUser.getUuid());
		insert(reBook);
	}

	public Page<Book> memberRecommendList(BookModel book) {
		return bookService.findRecommendList(book);
	}


}
