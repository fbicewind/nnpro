package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.common.Result;
import cn.nnnight.enums.Status;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.ArticleService;
import cn.nnnight.service.UserService;
import cn.nnnight.util.IPUtil;
import cn.nnnight.vo.RegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CommonController {

    public static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

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
    public ModelAndView index(@PathVariable("userId") int userId, HttpSession session, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("index");
        userService.addVisitHistory(IPUtil.getRemoteIp(request), userId);
        mv.addObject("all", articleService.getIndexInfo(userId));
        mv.addObject(Constants.IS_SELF, AuthUtil.getUserId() == userId);
        session.setAttribute(Constants.WHOLE_USER_ID, userId);
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
    public Result doLogin(@PathVariable String status, HttpServletRequest request) {
        userService.addLoginHistory(IPUtil.getRemoteIp(request), AuthUtil.getUserId(), status);
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

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Result doRegister(RegisterVo vo, HttpServletRequest request) {
        Result result = new Result();
        vo.setRegisterIp(IPUtil.getRemoteIp(request));
        boolean flag = userService.addUser(vo);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        if (flag) {
            result.setData(vo.getUsername());
        }
        return result;
    }
}
