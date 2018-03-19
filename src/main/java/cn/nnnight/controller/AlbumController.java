package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.entity.Album;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("userId") int userId, HttpSession session) {
        ModelAndView mv = new ModelAndView("album/album");
        mv.addObject(Constants.IS_SELF, AuthUtil.getUserId() == userId);
        mv.addObject("albumList", albumService.findAlbums(userId));
        session.setAttribute(Constants.WHOLE_USER_ID, userId);
        return mv;
    }

    @RequestMapping(value = "/detail/{albumId}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable("albumId") int albumId, HttpSession session) {
        ModelAndView mv = new ModelAndView("album/albumDetail");
        Album album = albumService.getAlbum(albumId);
        int userId = album.getUserId();
        mv.addObject(Constants.IS_SELF, AuthUtil.getUserId() == userId);
        mv.addObject("photoList", albumService.findPhotos(userId, albumId));
        session.setAttribute(Constants.WHOLE_USER_ID, userId);
        return mv;
    }

    @RequestMapping(value = "/getAlbum", method = RequestMethod.POST)
    @ResponseBody
    public Album getAlbum(@RequestParam("albumId") int albumId) {
        return albumService.getAlbum(albumId);
    }
}
