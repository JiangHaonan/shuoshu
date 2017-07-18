package ss.web.boss.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import ss.common.Result;
import ss.facade.user.service.BussinessException;

/**
 * Created by mutou on 2017/5/23.
 * 用于捕获异常，然后处理异常，以弹框或者返回json来处理相应的异常信息
 */
@ControllerAdvice
public class ExceptionHandle {
	
	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    //因为dubbo把服务中抛出的异常又包装回了RuntimeException,在没找到更好的办法前，我只能把自定义异常类写到了和服务接口类同一包下
    @ExceptionHandler(value = BussinessException.class)
    @ResponseBody
    public Result handle(BussinessException e) {
    	if(e instanceof BussinessException){
    		 Result re = new Result();
             re.setCode(e.getErrorCode());
             re.setMsg(e.getMessage());
             return re;
    	}else{
    		logger.debug("未知异常");
    		return null;
    	}
    }
}
