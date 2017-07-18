package ss.web.boss.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ss.common.util.PrincipalUtils;
import ss.common.util.ResultUtil;
import ss.facade.user.entity.model.UserModel;
import ss.facade.user.service.UserService;
import ss.web.boss.base.BaseController;
import ss.web.boss.security.FormAuthenticationFilter;

/**
 * Created by mutou on 2017/5/23.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register")
    public String register() {
        return "/register";
    }

    //注册，先对基本属性进行校验，然后校验密码和重复密码是否一致，最后校验用户是否已存在
    @RequestMapping(value = "/doRegister")
    @ResponseBody
    public Object doRegister(@Valid UserModel model, BindingResult bindingResult) {
    	if(bindingResult.hasErrors()){
    		return ResultUtil.error(999, bindingResult.getFieldError().getDefaultMessage());
    	}
        return userService.doRegister(model);
    }

    @RequestMapping(value = "/registerSuccess")
    public String registerSuccess() {
        return "/registerSuccess";
    }
    
    /**
     * 登陆页面
     */
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String loginPage() {
    	Object user = PrincipalUtils.getPrincipal();
        if (user != null){
            return "redirect:/index.html";
        }else{

            return "/loginPage";
        }
    }

    /**
     * 登陆失败到这个方法
     */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, HttpServletResponse response,Model model) {
    	Object operator = PrincipalUtils.getPrincipal();
		// 如果已经登录，则跳转到管理首页
		if(operator != null){
			 return "redirect:/index.html";
		}	
		
		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		return "/loginPage";
    }
}
