package cn.nnnight.service.impl;

import cn.nnnight.common.Constants;
import cn.nnnight.dao.MusicDao;
import cn.nnnight.entity.Music;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MusicServiceImpl implements MusicService {

    public static final Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class);

    @Autowired
    private MusicDao musicDao;

    @Override
    public void saveMusic(Music vo) {
        Music entity = null;
        if (vo.getId() == null || "".equals(vo.getId())) {
            entity = new Music();
            BeanUtils.copyProperties(vo, entity);
            entity.setId(UUID.randomUUID().toString());
            entity.setCreateTime(new Date());
            entity.setUserId(AuthUtil.getUserId());
            entity.setDelFlag(Constants.NO);
            musicDao.save(entity);
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
        return musicDao.findListByProperty("userId", userId, "createTime", false);
    }

    @Override
    public boolean deleteMusic(String musicId) {
        Music entity = musicDao.get(musicId);
        if (entity.getUserId() != AuthUtil.getUserId()) {
            return false;
        }
        musicDao.delete(entity);
        return true;
    }
}
