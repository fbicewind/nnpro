package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.UserService;
import cn.nnnight.vo.WorkExperienceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/aboutMe")
public class AboutMeController {

    @Autowired
    private UserService userService;

    @Value("${resume.isShow}")
    private boolean isShowResume;
    @Value("${resume.photoSrc}")
    private String photoSrc;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("userId") int userId, HttpSession session) {
        ModelAndView mv = new ModelAndView("aboutme/aboutMe");
        mv.addObject(Constants.IS_SELF, AuthUtil.getUserId() == userId);
        mv.addObject("user", userService.getUserByUserId(userId));// 用户信息
        mv.addObject(Constants.SHOW_RESUME, isShowResume);
        session.setAttribute(Constants.WHOLE_USER_ID, userId);
        return mv;
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public ModelAndView resume(HttpSession session) {
        ModelAndView mv = new ModelAndView("resume");
        mv.addObject("photosrc", photoSrc);
        session.setAttribute(Constants.WHOLE_USER_ID, 1);
        return mv;
    }

    @RequestMapping("/listExperience")
    @ResponseBody
    public List<WorkExperienceVo> listExperience() {
        return userService.findAllExperience();
    }

}
