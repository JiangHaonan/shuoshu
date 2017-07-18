package ss.web.boss.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ss.common.annotation.TokenValid;
import ss.common.orm.Page;
import ss.common.util.PrincipalUtils;
import ss.common.util.StringUtils;
import ss.common.web.fastdfs.FastDFSClient;
import ss.facade.book.entity.Book;
import ss.facade.book.entity.model.BookModel;
import ss.facade.book.service.RecommendBookService;
import ss.facade.user.entity.User;
import ss.web.boss.base.BaseController;
import ss.web.boss.global.Global;

/**
 * 
 * @author mutou
 * @date 2017年7月14日
 */
@Controller
@RequestMapping(value="/member")
public class MemberController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MemberController.class);
	
	@Autowired
	RecommendBookService recommendBookService;
	
	@RequestMapping(value = "/main")
    public String memberMain() {
        return "/main";
    }
	
	@RequestMapping(value = "/recommendBook")
    public String recommendBook() {
        return "user/bookRecommendPage";
    }
	
	@RequestMapping(value = "/memberRecommendList")
	@ResponseBody
    public Object memberRecommendList(BookModel book) {
		User user = (User) PrincipalUtils.getPrincipal();
		if(user == null){
    		return "redirect:/user/login.html";
    	}
		if(book.getPage()==null){
			book.setPage(new Page<Book>());
		}
		book.setUserUuid(user.getUuid());
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("data", recommendBookService.memberRecommendList(book));
    	map.put("storage_url", Global.getConfig("storage_url"));
        return map;
    }
	
	/**
     * 图片和表单提交
     */
    @RequestMapping(value = "/addRecommendBook",method = RequestMethod.POST)
    @TokenValid(value="recommendBook",href="/member/main.html")
    public String addRecommendBook(BookModel book ,MultipartFile file,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IllegalStateException, IOException{
    	User user = (User) PrincipalUtils.getPrincipal();
    	if(user == null){
    		return "redirect:/user/login.html";
    	}
        String fileId = "";
        String message = "";
        String type = "";
        if (file!=null) {// 判断上传的文件是否为空
            String fileName=file.getOriginalFilename();// 文件原名称
            // 判断文件类型
            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if (type!=null) {// 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                    
                	/*String basePath="/data/picture/" + DateUtils.formatDateTime(new Date());
                	// 项目在容器中实际发布运行的根路径
                    String realPath=request.getSession().getServletContext().getRealPath("/") + basePath;
                	File filedir = new File(realPath);
                	if(!filedir.exists()){
                		filedir.mkdirs();
                	}
                    // 自定义的文件名称
                    String trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
                    // 设置存放图片文件的路径
                    path=realPath+ "/" + trueFileName;
                    delPath = path;
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));*/
                	
                	//上传到分布式文件服务器
                    fileId = FastDFSClient.uploadFile(file.getInputStream(), fileName);
                }else {
                	message = "不是我们想要的文件类型,请按要求重新上传";
                }
            }else {
            	message = "文件类型为空";
            }
        }else {
        	message = "没有找到相对应的文件";
        }
        if(StringUtils.isNotBlank(message)){
        	request.setAttribute("message", message);
        	request.setAttribute("book", book);
        	return "user/bookRecommendPage";
        }
        
        book.setImage(fileId);
        book.setUploadType(BookModel.FASTDFSTYPE);
        logger.info("fileId----------------------:" + fileId);
        
        //如果遇到什么问题，再把图片删除掉
        try {
        	recommendBookService.addRecommendBook(book, user);
		} catch (Exception e) {
			FastDFSClient.deleteFile(fileId);
			message = e.getMessage();
			request.setAttribute("message", message);
        	request.setAttribute("book", book);
			return "user/bookRecommendPage";
		}
        
        
        return "user/bookAddSuccess";
    }
    
    
}
