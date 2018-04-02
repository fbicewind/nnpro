package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.ArticleService;
import cn.nnnight.service.UserService;
import cn.nnnight.util.IPUtil;
import cn.nnnight.vo.BlogAllVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("userId") int userId,
                              @RequestParam(value = "t", defaultValue = "a") String type, // 日志类型
                              @RequestParam(value = "i", defaultValue = "0") Integer typeId, // 日志分类
                              @RequestParam(value = "n", defaultValue = "1") Integer pageNo,
                              @RequestParam(value = "s", defaultValue = "10") Integer pageSize, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if (AuthUtil.getUserId() == userId) {
            mv.setViewName("article/myArticle");
            mv.addObject("type", type);
        } else {
            mv.setViewName("article/article");
        }
        mv.addObject("all", articleService.getArticleIndexInfo(userId, typeId, type, pageNo, pageSize));
        session.setAttribute(Constants.WHOLE_USER_ID, userId);
        return mv;
    }

    @RequestMapping(value = "/detail/{articleId}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable("articleId") int articleId,
                               @RequestParam(value = "i", defaultValue = "0") String isType,
                               @RequestParam(value = "t", defaultValue = "a") String diaryType, HttpServletRequest request,
                               HttpSession session) {
        ModelAndView mv = new ModelAndView("article/articleDetail");
        articleService.addViewRecord(articleId, AuthUtil.getUserId(), IPUtil.getRemoteIp(request));
        BlogAllVo vo = articleService.getArticleDetailInfo(articleId, isType, diaryType);
        mv.addObject("all", vo);
        mv.addObject(Constants.IS_SELF, AuthUtil.getUserId() == vo.getUserId());
        session.setAttribute(Constants.WHOLE_USER_ID, vo.getUserId());
        return mv;
    }
}
