package cn.nnnight.service.impl;

import cn.nnnight.common.Constants;
import cn.nnnight.dao.MusicDao;
import cn.nnnight.dao.UserDao;
import cn.nnnight.entity.Music;
import cn.nnnight.entity.User;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MusicServiceImpl implements MusicService {

    public static final Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class);

    @Autowired
    private MusicDao musicDao;
    @Autowired
    private UserDao userDao;

    @Override
    public void saveMusic(Music vo) {
        Music entity = null;
        int userId = AuthUtil.getUserId();
        if (vo.getId() == null || "".equals(vo.getId())) {
            entity = new Music();
            BeanUtils.copyProperties(vo, entity);
            entity.setId(UUID.randomUUID().toString());
            entity.setCreateTime(new Date());
            entity.setUserId(userId);
            entity.setDelFlag(Constants.NO);
            musicDao.save(entity);
            User user = userDao.get(userId);
            user.setMusicCount(user.getMusicCount() + 1);
            userDao.update(user);
        } else {
            entity = musicDao.get(vo.getId());
            entity.setLrcurl(vo.getLrcurl());
            entity.setSinger(vo.getSinger());
            entity.setTitle(vo.getTitle());
            entity.setType(vo.getType());
            entity.setUrl(vo.getUrl());
            entity.setDelFlag(Constants.NO);
            musicDao.update(entity);
        }
    }

    @Override
    public List<Music> getList(int userId) {
        Map<String, Object> values = new HashMap<>();
        values.put("delFlag", Constants.NO);
        values.put("userId", userId);
        return musicDao.findListByProperties(values, "createTime", false);
    }

    @Override
    public boolean deleteMusic(String musicId) {
        Music entity = musicDao.get(musicId);
        int userId = AuthUtil.getUserId();
        if (entity.getUserId() != userId) {
            return false;
        }
        entity.setDelFlag(Constants.YES);
        entity.setDeleteId(userId);
        entity.setDeleteTime(new Date());
        musicDao.update(entity);
        User user = userDao.get(userId);
        user.setMusicCount(user.getMusicCount() - 1);
        userDao.update(user);
        return true;
    }
}
