package cn.nnnight.service;

import cn.nnnight.entity.Emotion;
import cn.nnnight.entity.Goal;
import cn.nnnight.util.Pager;

import java.util.List;

public interface EmotionService {

    public Pager<Emotion> findEmotions(int userId, int pageNo, int pageSize);

    public List<Goal> getGoals(int userId);

    public boolean saveEmotion(String text, String type);

    public boolean updateGoal(String id, String type);
}
