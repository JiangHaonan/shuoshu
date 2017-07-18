package ss.common.annotation;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义token注解
 * @author mutou
 * @date 2017年7月15日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TokenValid {
	
	/**
	 * 设置token的name
	 * @return
	 */
	String value() default "";
	
	/**
	 * 校验token,需要表单校验的时候用
	 * 默认为false
	 */
	boolean check() default true;
	
	/**
	 * 校验token失败的时候，是否需要清除
	 * 默认为false
	 */
	boolean fileDelete() default false;
	
	/**
	 * 自定义链接，如果防重复失败，跳转的链接
	 * @return
	 */
	String href() default "";
}
