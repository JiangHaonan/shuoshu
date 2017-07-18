package ss.common.freemarker;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import ss.common.util.StringUtils;

/**
 * @author 之前公司的fangjun大神写的freemarker的token标签,提交时，配合@TokenValid和拦截器使用
 */
@Component
public class TokenDirective implements TemplateDirectiveModel {

	// 插入隐藏域模板
	private static final String TOKEN_INPUT = "<input type='hidden' name='%s' value='%s'/>";

	@Autowired(required = false)
	private HttpServletRequest request;

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		HttpSession session = request.getSession();
		String tokenName = params.get("name")!=null ? params.get("name").toString() : "";
		if (StringUtils.isNotBlank(tokenName)) {
			session.setAttribute(tokenName, UUID.randomUUID().toString());
			String token = (String) session.getAttribute(tokenName);
			env.getOut().write(String.format(TOKEN_INPUT, tokenName, token));
		}
	}

}
