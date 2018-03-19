package cn.nnnight.service;

import cn.nnnight.entity.Music;

import java.util.List;

public interface MusicService {

    public void saveMusic(Music vo);

    public List<Music> getList(int userId);

    public boolean deleteMusic(String musicId);
}
