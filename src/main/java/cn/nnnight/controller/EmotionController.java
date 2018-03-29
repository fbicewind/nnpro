package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.entity.Emotion;
import cn.nnnight.entity.Goal;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.EmotionService;
import cn.nnnight.service.UserService;
import cn.nnnight.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/emotion")
public class EmotionController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmotionService emotionService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("userId") int userId, HttpSession session) {
        ModelAndView mv = new ModelAndView("emotion/emotion");
        mv.addObject(Constants.IS_SELF, AuthUtil.getUserId() == userId);
        mv.addObject("user", userService.getUserByUserId(userId));// 用户信息
        session.setAttribute(Constants.WHOLE_USER_ID, userId);
        return mv;
    }

    @RequestMapping(value = "/getEmotions", method = RequestMethod.GET)
    @ResponseBody
    public Pager<Emotion> getEmotions(int userId, int pageNo) {
        return emotionService.findEmotions(userId, pageNo, 20);
    }

    @RequestMapping(value = "/getGoals", method = RequestMethod.GET)
    @ResponseBody
    public List<Goal> getGoals(int userId) {
        return emotionService.getGoals(userId);
    }
}
