package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.common.Result;
import cn.nnnight.enums.Status;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.UserService;
import cn.nnnight.util.UploadUtil;
import cn.nnnight.vo.AvatarVo;
import cn.nnnight.vo.WorkExperienceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/aboutMe")
public class AboutMeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("userId") int userId, HttpSession session) {
        ModelAndView mv = new ModelAndView("aboutme/aboutMe");
        mv.addObject(Constants.IS_SELF, AuthUtil.getUserId() == userId);
        mv.addObject("user", userService.getUserByUserId(userId));// 用户信息
        session.setAttribute(Constants.WHOLE_USER_ID, userId);
        return mv;
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public ModelAndView resume(HttpSession session) {
        session.setAttribute(Constants.WHOLE_USER_ID, 1);
        return new ModelAndView("resume");
    }

    @RequestMapping("/listExperience")
    @ResponseBody
    public List<WorkExperienceVo> listExperience() {
        return userService.findAllExperience();
    }

}
