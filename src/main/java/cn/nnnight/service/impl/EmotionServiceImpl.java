package cn.nnnight.service.impl;

import cn.nnnight.common.Constants;
import cn.nnnight.dao.EmotionDao;
import cn.nnnight.dao.GoalDao;
import cn.nnnight.entity.Emotion;
import cn.nnnight.entity.Goal;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.EmotionService;
import cn.nnnight.util.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmotionServiceImpl implements EmotionService {

    public static final Logger logger = LoggerFactory.getLogger(EmotionServiceImpl.class);

    @Autowired
    private EmotionDao emotionDao;
    @Autowired
    private GoalDao goalDao;

    @Override
    public Pager<Emotion> findEmotions(int userId, int pageNo, int pageSize) {
        Map<String, Object> values = new HashMap<>();
        values.put("userId", userId);
        values.put("delFlag", Constants.NO);
        return emotionDao.selectPageByProterties(pageNo, pageSize, values, "createTime", true);
    }

    @Override
    public List<Goal> getGoals(int userId) {
        Map<String, Object> values = new HashMap<>();
        values.put("userId", userId);
        values.put("delFlag", Constants.NO);
        return goalDao.findListByProperties(values, "startTime", true);
    }

    @Override
    public boolean saveEmotion(String text, String type) {
        boolean flag = false;
        int userId = AuthUtil.getUserId();
        try {
            if (Constants.STRING_TWO.equals(type)) {
                Goal entity = new Goal();
                entity.setGoal(text);
                entity.setStartTime(new Date());
                entity.setUserId(userId);
                entity.setStatus("0");
                entity.setId(UUID.randomUUID().toString());
                entity.setDelFlag(Constants.NO);
                goalDao.save(entity);
                flag = true;
            } else if (Constants.STRING_ONE.equals(type)) {
                Emotion entity = new Emotion();
                entity.setCreateTime(new Date());
                entity.setEmotion(text);
                entity.setId(UUID.randomUUID().toString());
                entity.setPraiseCount(0);
                entity.setUserId(userId);
                entity.setDelFlag(Constants.NO);
                emotionDao.save(entity);
                flag = true;
            }
        } catch (Exception e) {
            logger.error("Save emotion error: ", e);
        }
        return flag;
    }

    @Override
    public boolean updateGoal(String id, String type) {
        boolean flag = false;
        int currentId = AuthUtil.getUserId();
        Goal goal = goalDao.get(id);
        if (goal.getUserId() == currentId) {
            goal.setStatus(type);
            goal.setEndTime(new Date());
            goalDao.update(goal);
        }
        return flag;
    }
}
