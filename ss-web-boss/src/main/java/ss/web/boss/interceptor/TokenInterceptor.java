package ss.web.boss.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Objects;

import ss.common.annotation.TokenValid;
import ss.common.util.SessionUtils;

/**
 * token拦截器
 * @author mutou
 * @date 2017年7月15日
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Session session = SessionUtils.getSession();
            //查看方法是否加了TokenValid注解
            TokenValid annotation = method.getAnnotation(TokenValid.class);
            if (annotation != null) {
                String tokenName = annotation.value();
                /*boolean save = annotation.save();*/
                boolean check = annotation.check();
                /*if(save){
                	//这边先简单的用UUID来作为token验证码
                	session.setAttribute(tokenName, UUID.randomUUID().toString());
                }*/
                if(check){
                	if(!Objects.equal(request.getParameter(tokenName), session.getAttribute(tokenName))){
                		response.getWriter().print("<script languge=javascript>alert('请勿重复提交！')</script>");
                		if(StringUtils.isNotBlank(annotation.href())){
                			response.getWriter().print("\"<script>window.location ='"+annotation.href()+"'</script>\"");
                		}
						return false;
                	}else{
                		//清除token
                		session.removeAttribute(tokenName);
                	}
                }
            }
        }
        return true;
    }
}