package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.common.Result;
import cn.nnnight.entity.Album;
import cn.nnnight.entity.User;
import cn.nnnight.enums.Status;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.AlbumService;
import cn.nnnight.service.ArticleService;
import cn.nnnight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/validate")
public class ValidateController {

    @Autowired
    private UserService userService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/username")
    public Result username(@RequestParam String username) {
        Result result = new Result();
        User vo = new User();
        vo.setUsername(username);
        User user = userService.getUserByParam(vo);
        if (user == null) {
            result.setCode(Status.SUCCESS.getCode());
        } else {
            result.setCode(Status.FAILURE.getCode());
        }
        return result;
    }

    @RequestMapping("/nickname")
    public Result nickname(@RequestParam String nickname) {
        Result result = new Result();
        User vo = new User();
        vo.setNickname(nickname);
        User user = userService.getUserByParam(vo);
        if (user == null) {
            result.setCode(Status.SUCCESS.getCode());
        } else {
            result.setCode(Status.FAILURE.getCode());
        }
        return result;
    }

    @RequestMapping("/albumName")
    public Result albumName(@RequestParam String albumName, String albumId) {
        Result result = new Result();
        List<Album> albums = albumService.findByAlbumName(albumName);
        if (albums == null || albums.isEmpty()) {
            result.setCode(Status.SUCCESS.getCode());
        } else if (albumId != null && albumId.equals(albums.get(0).getId() + "")) {
            result.setCode(Status.SUCCESS.getCode());
        } else {
            result.setCode(Status.FAILURE.getCode());
        }
        return result;
    }

    @RequestMapping("/newType")
    public Result newType(@RequestParam String typeName) {
        Result result = new Result();
        if (Constants.MY_BLOG_TYPE.equals(typeName)) {
            result.setCode(Status.FAILURE.getCode());
        } else {
            result.setCode(articleService.isTypeExist(typeName) ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        }
        return result;
    }
}
