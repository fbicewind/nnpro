package cn.nnnight.controller;

import cn.nnnight.security.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class CommonController {

    public static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    /**
     * 进入系统时，如果已登入，进入自己主页，否则跳转至默认用户页
     *
     * @return
     */
    @RequestMapping(value = "/go", method = RequestMethod.GET)
    public String go() {
        return "redirect:/u/" + (AuthUtil.getUserId() == 0 ? "0" : AuthUtil.getUserId());
    }

    /**
     * 跳转用户主页
     *
     * @param userId
     * @param session
     * @return
     */
    @RequestMapping(value = "/u/{userId}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("userId") int userId, HttpSession session) {
        ModelAndView mv = new ModelAndView("index");
        logger.info(userId + "");
        return mv;
    }

    @RequestMapping(value = "/error/{port}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("port") int port) {
        System.out.println(port);
        return new ModelAndView("index");
    }
}
