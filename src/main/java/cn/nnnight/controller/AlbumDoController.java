package cn.nnnight.controller;

import cn.nnnight.common.Constants;
import cn.nnnight.common.Result;
import cn.nnnight.entity.Album;
import cn.nnnight.enums.Status;
import cn.nnnight.service.AlbumService;
import cn.nnnight.util.UploadUtil;
import cn.nnnight.vo.AlbumVo;
import cn.nnnight.vo.NewPhotoVo;
import cn.nnnight.vo.PhotoOperateVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/albumDo")
public class AlbumDoController {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private UploadUtil uploadUtil;

    @RequestMapping(value = "/saveAlbum", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAlbum(AlbumVo vo) {
        Result result = new Result();
        boolean flag = albumService.saveOrUpdateAlbum(vo);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }

    @RequestMapping("/uploadPhoto")
    @ResponseBody
    public Result uploadPhoto(@RequestParam("photo") MultipartFile photo, @RequestParam("albumId") int albumId) {
        Result result = new Result();
        String fileName = uploadUtil.uploadPhotos(photo);
        if (StringUtils.isNotBlank(fileName)) {
            boolean flag = albumService.savePhoto(albumId, fileName);
            result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
            NewPhotoVo vo = new NewPhotoVo();
            vo.setPhoto(fileName);
            vo.setIndex(albumService.getNextIndex(albumId));
            result.setData(vo);
        }
        return result;
    }

    @RequestMapping("/delAlbum")
    @ResponseBody
    public Result delAlbum(@RequestParam("albumId") int albumId) {
        Result result = new Result();
        boolean flag = albumService.deleteAlbum(albumId);
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }

    @RequestMapping("/operatePhoto")
    @ResponseBody
    public Result operatePhoto(@RequestBody PhotoOperateVo vo) {
        Result result = new Result();
        boolean flag = false;
        if (Constants.STRING_ONE.equals(vo.getOperate())) {
            flag = albumService.deletePhotos(vo.getIds());
        } else if (Constants.STRING_TWO.equals(vo.getOperate())) {
            flag = albumService.setCover(vo.getIds());
        }
        result.setCode(flag ? Status.SUCCESS.getCode() : Status.FAILURE.getCode());
        return result;
    }
}
