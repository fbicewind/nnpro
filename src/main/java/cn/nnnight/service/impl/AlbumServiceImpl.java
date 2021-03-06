package cn.nnnight.service.impl;

import cn.nnnight.common.Constants;
import cn.nnnight.dao.AlbumDao;
import cn.nnnight.dao.AlbumPhotoDao;
import cn.nnnight.dao.UserDao;
import cn.nnnight.entity.Album;
import cn.nnnight.entity.AlbumPhoto;
import cn.nnnight.entity.User;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.AlbumService;
import cn.nnnight.vo.AlbumVo;
import cn.nnnight.vo.NewPhotoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlbumServiceImpl implements AlbumService {

    public static final Logger logger = LoggerFactory.getLogger(AlbumServiceImpl.class);

    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private AlbumPhotoDao albumPhotoDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Album> findAlbums(int userId) {
        Map<String, Object> values = new HashMap<>();
        values.put("userId", userId);
        if (userId != AuthUtil.getUserId()) {
            values.put("publicFlag", Constants.YES);
        }
        values.put("delFlag", Constants.NO);
        return albumDao.findListByProperties(values, "createTime", false);
    }

    @Override
    public Album getAlbum(int id) {
        return albumDao.get(id);
    }

    @Override
    public List<AlbumPhoto> findPhotos(int userId, int albumId) {
        Map<String, Object> values = new HashMap<>();
        values.put("delFlag", Constants.NO);
        values.put("userId", userId);
        values.put("albumId", albumId);
        return albumPhotoDao.findListByProperties(values, "createTime", false);
    }

    @Override
    public List<Album> findByAlbumName(String albumName) {
        Map<String, Object> values = new HashMap<>();
        values.put("userId", AuthUtil.getUserId());
        values.put("albumName", albumName);
        values.put("delFlag", Constants.NO);
        return albumDao.findListByProperties(values);
    }

    @Override
    public boolean saveOrUpdateAlbum(AlbumVo vo) {
        boolean flag = false;
        try {
            Album entity = null;
            if (vo.getId() == null || "".equals(vo.getId()) || "0".equals(vo.getId())) {
                entity = new Album();
                entity.setCreateTime(new Date());
                entity.setUserId(AuthUtil.getUserId());
                entity.setCoverImg(Constants.DEFAULT_COVER);
                entity.setDelFlag(Constants.NO);
            } else {
                entity = albumDao.get(Integer.valueOf(vo.getId()));
            }
            BeanUtils.copyProperties(vo, entity);
            albumDao.saveOrUpdate(entity);
            flag = true;
        } catch (BeansException e) {
            logger.error("Save album error: ", e);
        }
        return flag;
    }

    @Override
    public AlbumPhoto savePhoto(int albumId, String fileName) {
        int userId = AuthUtil.getUserId();
        AlbumPhoto entity = new AlbumPhoto();
        entity.setAlbumId(albumId);
        entity.setCreateTime(new Date());
        entity.setPhoto(fileName);
        entity.setUserId(userId);
        entity.setDelFlag(Constants.NO);
        albumPhotoDao.save(entity);
        Album album = albumDao.get(albumId);
        album.setPhotoCount(album.getPhotoCount() + 1);
        albumDao.update(album);
        User user = userDao.get(userId);
        user.setPhotoCount(user.getPhotoCount() + 1);
        userDao.update(user);
        return entity;
    }

    @Override
    public boolean deleteAlbum(int albumId) {
        boolean flag = false;
        try {
            int userId = AuthUtil.getUserId();
            Date now = new Date();
            Album album = albumDao.get(albumId);
            if (userId != album.getUserId()) {
                return flag;
            }
            album.setDelFlag(Constants.YES);
            album.setDeleteId(userId);
            album.setDeleteTime(now);
            albumDao.update(album);
            Map<String, Object> values = new HashMap<>();
            values.put("albumId", albumId);
            values.put("delFlag", Constants.NO);
            List<AlbumPhoto> photos = albumPhotoDao.findListByProperties(values);
            for (AlbumPhoto photo : photos) {
                photo.setDelFlag(Constants.YES);
                photo.setDeleteId(userId);
                photo.setDeleteTime(now);
                albumPhotoDao.update(photo);
            }
            User user = userDao.get(userId);
            user.setPhotoCount(user.getPhotoCount() - album.getPhotoCount());
            userDao.update(user);
            flag = true;
        } catch (Exception e) {
            logger.error("Delete album error: ", e);
        }
        return flag;
    }

    @Override
    public int getNextIndex(int albumId) {
        Map<String, Object> values = new HashMap<>();
        values.put("albumId", albumId);
        values.put("delFlag", Constants.NO);
        return albumPhotoDao.countByProperties(values);
    }

    @Override
    public boolean deletePhotos(List<String> ids) {
        boolean flag = false;
        try {
            Date now = new Date();
            int userId = AuthUtil.getUserId();
            int albumId = 0;
            for (String id : ids) {
                AlbumPhoto photo = albumPhotoDao.get(id);
                photo.setDelFlag(Constants.YES);
                photo.setDeleteTime(now);
                photo.setDeleteId(userId);
                albumPhotoDao.update(photo);
                albumId = photo.getAlbumId();
            }
            Album album = albumDao.get(albumId);
            album.setPhotoCount(album.getPhotoCount() - ids.size());
            Map<String, Object> values = new HashMap<>();
            values.put("userId", userId);
            values.put("delFlag", Constants.NO);
            values.put("photo", album.getCoverImg());
            List<AlbumPhoto> coverlist = albumPhotoDao.findListByProperties(values);
            if (coverlist == null || coverlist.isEmpty()) {
                album.setCoverImg(Constants.DEFAULT_COVER);
            }
            albumDao.update(album);
            User user = userDao.get(userId);
            user.setPhotoCount(user.getPhotoCount() - ids.size());
            userDao.update(user);
            flag = true;
        } catch (Exception e) {
            logger.error("Delete photo error: ", e);
        }
        return flag;
    }

    @Override
    public boolean setCover(List<String> ids) {
        boolean flag = false;
        try {
            AlbumPhoto photo = albumPhotoDao.get(ids.get(0));
            Album album = albumDao.get(photo.getAlbumId());
            album.setCoverImg(photo.getPhoto());
            albumDao.update(album);
            flag = true;
        } catch (Exception e) {
            logger.error("Delete photo error: ", e);
        }
        return flag;
    }

}
