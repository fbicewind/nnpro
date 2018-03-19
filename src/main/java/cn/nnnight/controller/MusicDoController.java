package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.common.Result;
import cn.nnnight.entity.Music;
import cn.nnnight.enums.Status;
import cn.nnnight.service.MusicService;
import cn.nnnight.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/musicDo")
public class MusicDoController {

    @Autowired
    private MusicService musicService;
    @Autowired
    private UploadUtil uploadUtil;

    @RequestMapping(value = "/saveMusic", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMusic(@RequestParam(value = "urlfile", required = false) MultipartFile urlfile,
                            @RequestParam(value = "lrcfile", required = false) MultipartFile lrcfile,
                            @RequestParam(value = "coverfile", required = false) MultipartFile coverfile, Music
                                    vo) {
        Result result = new Result();
        if (urlfile != null && urlfile.getSize() > 0) {
            String url = uploadUtil.uploadMusicFile(urlfile);
            vo.setUrl(url);
        }
        if (lrcfile != null && lrcfile.getSize() > 0) {
            String lrcurl = uploadUtil.uploadMusicFile(lrcfile);
            vo.setLrcurl(lrcurl);
        }
        if (coverfile != null && coverfile.getSize() > 0) {
            String cover = uploadUtil.uploadMusicCover(coverfile);
            vo.setCover(cover);
        }
        musicService.saveMusic(vo);
        result.setCode(Status.SUCCESS.getCode());
        return result;
    }

    @RequestMapping(value = "deleteMusic", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteMusic(@RequestParam String musicId) {
        Result result = new Result();
        boolean flag = musicService.deleteMusic(musicId);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }
}
