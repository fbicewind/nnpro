package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.common.Result;
import cn.nnnight.entity.Article;
import cn.nnnight.entity.ArticleComment;
import cn.nnnight.entity.ArticleType;
import cn.nnnight.enums.Status;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.ArticleService;
import cn.nnnight.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping("/articleDo")
public class ArticleDoController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UploadUtil uploadUtil;

    @RequestMapping(value = "/mark", method = RequestMethod.GET)
    public ModelAndView mark(@RequestParam(value = "articleId", defaultValue = "0") Integer articleId, HttpSession session) {
        ModelAndView mv = new ModelAndView("article/articlePublish");
        mv.addObject("all", articleService.getArticleMarkInfo(AuthUtil.getUserId(), articleId));
        session.setAttribute(Constants.WHOLE_USER_ID, AuthUtil.getUserId());
        return mv;
    }

    @RequestMapping("/saveArticle")
    @ResponseBody
    public Result saveArticle(Article vo) {
        Result result = new Result();
        Article article = articleService.saveArticle(vo);
        result.setCode(Status.SUCCESS.getCode());
        result.setData(article);
        return result;
    }

    @RequestMapping("/saveType")
    @ResponseBody
    public ArticleType saveType(@RequestParam String typeName) {
        return articleService.saveArticleType(typeName);
    }

    @RequestMapping("/saveComment")
    @ResponseBody
    public Result saveComment(ArticleComment articleComment) {
        Result result = new Result();
        boolean flag = articleService.saveComment(articleComment);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }

    @RequestMapping("/praiseArticle")
    @ResponseBody
    public Result praiseArticle(@RequestParam("state") Integer state, @RequestParam("articleId") Integer articleId) {
        Result result = new Result();
        boolean flag = articleService.praiseArticle(articleId, state);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }

    @RequestMapping("/favoriteArticle")
    @ResponseBody
    public Result favoriteArticle(@RequestParam("state") Integer state, @RequestParam("articleId") Integer articleId) {
        Result result = new Result();
        boolean flag = articleService.favoriteArticle(articleId, state);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }

    @RequestMapping("/delArticle")
    @ResponseBody
    public Result delArticle(@RequestParam("articleId") Integer articleId) {
        Result result = new Result();
        boolean flag = articleService.delArticle(articleId);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("image") MultipartFile image, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        // 文件类型限制
        String[] allowedType = {"image/bmp", "image/gif", "image/jpeg", "image/png"};
        boolean allowed = Arrays.asList(allowedType).contains(image.getContentType());
        if (!allowed) {
            response.getWriter().write("error|不支持的类型");
            return;
        }
        // 图片大小限制
        if (image.getSize() > 5 * 1024 * 1024) {
            response.getWriter().write("error|图片大小不能超过5M");
            return;
        }
        String returnPath = uploadUtil.uploadBlogImage(image);
        response.getWriter().write(returnPath);
    }
}
