package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.ArticleService;
import cn.nnnight.util.IPUtil;
import cn.nnnight.util.Pager;
import cn.nnnight.vo.ArticleCommentVo;
import cn.nnnight.vo.BlogAllVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("userId") int userId,
                              @RequestParam(value = "t", defaultValue = "a") String type, // 日志类型
                              @RequestParam(value = "i", defaultValue = "0") Integer typeId, // 日志分类
                              @RequestParam(value = "n", defaultValue = "1") Integer pageNo,
                              @RequestParam(value = "s", defaultValue = "10") Integer pageSize, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        type = fmtType(type);
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

    private String fmtType(String type) {
        boolean flag1 = !Constants.DRAFTDIARY.equals(type) && !Constants.FAVORABLEDIARY.equals(type);
        boolean flag2 = !Constants.PRIVATEDIARY.equals(type) && !Constants.MYDIARY.equals(type);
        if (flag1 && flag2) {
            return Constants.MYDIARY;
        } else {
            return type;
        }
    }

    @RequestMapping(value = "/getComments", method = RequestMethod.POST)
    @ResponseBody
    public Pager<ArticleCommentVo> getComments(@RequestParam int articleId, @RequestParam int pageNo) {
        return articleService.getComments(articleId, pageNo);
    }
}
