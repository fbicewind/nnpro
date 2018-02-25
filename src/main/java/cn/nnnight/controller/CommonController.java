package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.common.Result;
import cn.nnnight.enums.Status;
import cn.nnnight.security.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
        return "redirect:/u/" + (AuthUtil.getUserId() == 0 ? Constants.STRING_ONE : AuthUtil.getUserId());
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
        return mv;
    }

    @RequestMapping(value = "/error/{port}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("port") int port) {
        return new ModelAndView("index");
    }

    /**
     * 登录成功失败返回结果
     *
     * @param status
     * @return
     */
    @RequestMapping(value = "/login/{status}")
    @ResponseBody
    public Result doLogin(@PathVariable String status) {
        Result result = new Result();
        result.setCode(Constants.SUCCESS.equals(status) ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }

    /**
     * 退出成功返回结果
     *
     * @return
     */
    @RequestMapping(value = "/logout/exit")
    @ResponseBody
    public Result exit() {
        Result result = new Result();
        result.setCode(Status.SUCCESS.getCode());
        return result;
    }
}
