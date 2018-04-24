package cn.nnnight.service;

import cn.nnnight.entity.User;
import cn.nnnight.vo.RegisterVo;
import cn.nnnight.vo.WorkExperienceVo;

import java.util.List;

public interface UserService {

    public User getUserByUserId(int userId);

    public boolean addUser(RegisterVo vo);

    public User getUserByParam(User vo);

    public boolean modifyAvatar(String avatar);

    public boolean modifyInfo(RegisterVo vo);

    public Integer modifyPassword(String password, String oldPassword);

    public List<WorkExperienceVo> findAllExperience();

    public void addVisitHistory(String ip, int userId);

    public void addLoginHistory(String ip, int userId, String status);
}
