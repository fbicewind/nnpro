package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.common.Result;
import cn.nnnight.enums.Status;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.UserService;
import cn.nnnight.util.UploadUtil;
import cn.nnnight.vo.AvatarVo;
import cn.nnnight.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
@RequestMapping("/aboutMeDo")
public class AboutMeDoController {

    @Autowired
    private UserService userService;
    @Autowired
    private UploadUtil uploadUtil;

    @RequestMapping(value = "/modifyAvatar", method = RequestMethod.GET)
    public ModelAndView modifyAvatar(HttpSession session) {
        session.setAttribute(Constants.WHOLE_USER_ID, AuthUtil.getUserId());
        return new ModelAndView("aboutme/modifyAvatar");
    }

    @RequestMapping(value = "/modifyInfo", method = RequestMethod.GET)
    public ModelAndView modifyInfo(HttpSession session) {
        ModelAndView mv = new ModelAndView("aboutme/modifyInfo");
        mv.addObject("user", userService.getUserByUserId(AuthUtil.getUserId()));
        session.setAttribute(Constants.WHOLE_USER_ID, AuthUtil.getUserId());
        return mv;
    }

    @RequestMapping(value = "/modifyPwd", method = RequestMethod.GET)
    public ModelAndView changePassword(HttpSession session) {
        session.setAttribute(Constants.WHOLE_USER_ID, AuthUtil.getUserId());
        return new ModelAndView("aboutme/modifyPassword");
    }

    @RequestMapping("/uploadTempAvatar")
    @ResponseBody
    public Result uploadTempAvatar(@RequestParam("avatar") MultipartFile avatar) {
        Result result = new Result();
        String[] allowedType = {"image/bmp", "image/gif", "image/jpeg", "image/png"};
        boolean allowed = Arrays.asList(allowedType).contains(avatar.getContentType());
        if (!allowed) {
            result.setCode(Status.FAILURE.getCode());
        } else {
            String url = uploadUtil.uploadTempAvatar(avatar);
            result.setCode(Status.SUCCESS.getCode());
            result.setData(url);
        }
        return result;
    }

    @RequestMapping("/saveAvatar")
    @ResponseBody
    public Result saveAvatar(@RequestBody AvatarVo vo) {
        Result result = new Result();
        String avatar = uploadUtil.uploadAvatar(vo);
        if (userService.modifyAvatar(avatar)) {
            result.setCode(Status.SUCCESS.getCode());
        }
        return result;
    }

    @RequestMapping("/modifyInfo")
    @ResponseBody
    public Result modifyInfo(RegisterVo vo) {
        Result result = new Result();
        boolean flag = userService.modifyInfo(vo);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }

    @RequestMapping("/modifyPwd")
    @ResponseBody
    public Result modifyPwd(String oldPassword, String password) {
        Result result = new Result();
        result.setCode(userService.modifyPassword(password, oldPassword) + "");
        return result;
    }
}
