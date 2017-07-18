package ss.web.boss.action;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mutou on 2017/5/22.
 */

@Controller
public class IndexController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

}
