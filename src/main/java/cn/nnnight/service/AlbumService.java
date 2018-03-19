package cn.nnnight.service;

import cn.nnnight.entity.Album;
import cn.nnnight.entity.AlbumPhoto;
import cn.nnnight.vo.AlbumVo;

import java.util.List;

public interface AlbumService {

    public Album getAlbum(int id);

    public List<Album> findAlbums(int userId);

    public List<AlbumPhoto> findPhotos(int userId, int albumId);

    public List<Album> findByAlbumName(String albumName);

    public boolean saveOrUpdateAlbum(AlbumVo vo);

    public boolean savePhoto(int albumId, String fileName);

    public boolean deleteAlbum(int albumId);
}
