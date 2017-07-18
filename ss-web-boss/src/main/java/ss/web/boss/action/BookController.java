package ss.web.boss.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ss.common.orm.Page;
import ss.facade.book.entity.Book;
import ss.facade.book.entity.model.BookModel;
import ss.facade.book.service.BookService;
import ss.web.boss.global.Global;

/**
 * @author mutou
 * @date 2017年7月14日
 */
@Controller
@RequestMapping(value="/book")
public class BookController {
	
	private final static Logger LOGGE = Logger.getLogger(BookController.class);
	
	@Autowired
    private BookService bookService;
	
    @RequestMapping(value = "/indexBookList")
    @ResponseBody
    public Object getIndexList(BookModel book) {
    	if(book.getPage()==null){
    		 book.setPage(new Page<Book>());
 		}
    	LOGGE.info("========================================="+book.getTitle());
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("data", bookService.findPageData(book));
    	map.put("storage_url", Global.getConfig("storage_url"));
        return map;
    }
    
    @RequestMapping(value = "/bookDetail")
    public ModelAndView bookDetail(String uuid) {
    	ModelAndView modelAndView = new ModelAndView();  
        modelAndView.addObject("book", bookService.findByUuid(uuid));  
        modelAndView.setViewName("/book/bookdetail");  
        return modelAndView;  
    }

    @RequestMapping(value = "/insertDouPanBook")
    public void insertTestBookByBookName(@RequestParam("bookName") String bookName) throws Exception {
        URL url = new URL("https://api.douban.com/v2/book/search?q=" + bookName);
        URLConnection uc = url.openConnection();
        InputStream is = uc.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
        String line = "";
        StringBuffer result = new StringBuffer();
        System.out.println("获取返回数据:");
        while ((line = br.readLine()) != null) {
            // 读取返回数据，分行读取
            result.append(line.trim());
        }
        System.out.println(result.toString());
        br.close();
        is.close();
        JSONObject object = (JSONObject) JSON.parse(result.toString());
        JSONArray array = (JSONArray) object.get("books");
        Iterator<Object> it = array.iterator();
        while(it.hasNext()){
            JSONObject ob = (JSONObject) it.next();
            Book book = new Book();
            book.setAuthor(ob.get("author").toString());
            book.setImage((String)ob.get("image"));
            book.setTitle((String)ob.get("title"));
            book.setSubtitle((String)ob.get("subtitle"));
            book.setSummary((String)ob.get("summary"));
            book.setPubdate((String)ob.get("pubdate"));
            book.setPublisher((String)ob.get("publisher"));
            book.setUploadType(BookModel.APITYPE);
            bookService.insert(book);
        }
    }
}
