package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.entity.Music;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.MusicService;
import cn.nnnight.service.UserService;
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
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("userId") int userId, HttpSession session) {
        ModelAndView mv = new ModelAndView("music/music");
        mv.addObject(Constants.IS_SELF, AuthUtil.getUserId() == userId);
        session.setAttribute(Constants.WHOLE_USER_ID, userId);
        return mv;
    }

    @RequestMapping(value = "getList", method = RequestMethod.GET)
    @ResponseBody
    public List<Music> getList(int userId) {
        return musicService.getList(userId);
    }
}
