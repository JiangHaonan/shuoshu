package ss.service.book.mapper;

import java.util.List;

import ss.common.core.mapper.BaseMapper;
import ss.facade.book.entity.Book;
import ss.facade.book.entity.model.BookModel;

public interface BookMapper extends BaseMapper<Book>{

	List<Book> findPageData(BookModel book);

	List<Book> findRecommendList(BookModel book);

}
