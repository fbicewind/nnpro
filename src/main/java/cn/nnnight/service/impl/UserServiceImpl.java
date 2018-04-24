package cn.nnnight.service.impl;

import cn.nnnight.common.Constants;
import cn.nnnight.dao.BlogVisitorDao;
import cn.nnnight.dao.ResumeWorkExperienceDao;
import cn.nnnight.dao.UserDao;
import cn.nnnight.dao.UserLoginHistoryDao;
import cn.nnnight.entity.BlogVisitor;
import cn.nnnight.entity.ResumeWorkExperience;
import cn.nnnight.entity.User;
import cn.nnnight.entity.UserLoginHistory;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.security.CustomUser;
import cn.nnnight.security.MyPasswordEncode;
import cn.nnnight.service.UserService;
import cn.nnnight.vo.RegisterVo;
import cn.nnnight.vo.WorkExperienceVo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private ResumeWorkExperienceDao resumeWorkExperienceDao;
    @Autowired
    private BlogVisitorDao blogVisitorDao;
    @Autowired
    private UserLoginHistoryDao userLoginHistoryDao;

    @Override
    public User getUserByUserId(int userId) {
        return userDao.get(userId);
    }

    @Override
    public boolean addUser(RegisterVo vo) {
        boolean flag = true;
        User entity = userDao.getUniqueResult("username", vo.getUsername());
        if (entity != null) {
            flag = false;
            return flag;
        }
        Date now = new Date();
        MyPasswordEncode encode = new MyPasswordEncode();
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        user.setRegisterTime(now);
        user.setPassword(encode.encode(vo.getPassword()));
        user.setAvatar("head000.png");
        if (vo.getBirthday() != null && !"".equals(vo.getBirthday())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birthday = sdf.parse(vo.getBirthday());
                user.setBirthday(birthday);
            } catch (Exception e) {
                flag = false;
            }
        }
        user.setDelFlag(Constants.NO);
        user.setRole(1);
        user.setState(1);
        userDao.save(user);
        return flag;
    }

    @Override
    public User getUserByParam(User vo) {
        Map<String, Object> values = new HashMap<>();
        if (vo.getUsername() != null && !"".equals(vo.getUsername())) {
            values.put("username", vo.getUsername());
        }
        if (vo.getNickname() != null && !"".equals(vo.getNickname())) {
            values.put("nickname", vo.getNickname());
        }
        values.put("delFlag", Constants.NO);
        return userDao.getUniqueResult(values);
    }

    @Override
    public boolean modifyAvatar(String avatar) {
        boolean flag = false;
        try {
            User user = userDao.get(AuthUtil.getUserId());
            user.setAvatar(avatar);
            userDao.update(user);
            CustomUser userDetails = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userDetails.setAvatar(avatar);
            flag = true;
        } catch (Exception e) {
            logger.error("Modify avatar error: ", e);
        }
        return flag;
    }

    @Override
    public boolean modifyInfo(RegisterVo vo) {
        boolean flag = true;
        try {
            int userId = AuthUtil.getUserId();
            Date updateTime = new Date();
            User user = userDao.get(userId);
            user.setRemark(vo.getRemark());
            user.setUpdateTime(updateTime);
            user.setEmail(vo.getEmail());
            user.setMobile(vo.getMobile());
            user.setProfession(vo.getProfession());
            user.setGender(vo.getGender());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = sdf.parse(vo.getBirthday());
            user.setBirthday(birthday);
            userDao.update(user);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Integer modifyPassword(String password, String oldPassword) {
        int result = 0;
        try {
            User user = userDao.get(AuthUtil.getUserId());
            MyPasswordEncode encode = new MyPasswordEncode();
            if (encode.matches(oldPassword, user.getPassword())) {
                user.setPassword(encode.encode(password));
                userDao.update(user);
                result = 1;
            } else {
                result = 2;
            }
        } catch (Exception e) {
            logger.error("Modify password error: ", e);
        }
        return result;
    }

    @Override
    public List<WorkExperienceVo> findAllExperience() {
        List<ResumeWorkExperience> list = resumeWorkExperienceDao.findAll("startDate", false);
        List<WorkExperienceVo> vos = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
        for (ResumeWorkExperience entity : list) {
            WorkExperienceVo vo = new WorkExperienceVo();
            StringBuilder workTechnology = new StringBuilder("");
            BeanUtils.copyProperties(entity, vo);
            for (String tech : vo.getWorkTechnology().split(",")) {
                workTechnology.append("<span class='resume-inline'>").append(tech).append("</span>").append(" + ");
            }
            vo.setWorkTechnology(workTechnology.substring(0, workTechnology.length() - 3));
            vo.setStartDate(sdf.format(entity.getStartDate()));
            if (entity.getEndDate() != null && !"".equals(entity.getEndDate())) {
                vo.setEndDate(sdf.format(entity.getEndDate()));
            } else {
                vo.setEndDate(Constants.NOW);
            }
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public void addVisitHistory(String ip, int userId) {
        Runnable historyThread = () -> {
            Date now = new Date();
            SessionFactory sessionFactory = (SessionFactory) ContextLoader.getCurrentWebApplicationContext().getBean("sessionFactory");
            Session session = sessionFactory.openSession();
            Transaction tran = session.beginTransaction();
            Map<String, Object> values = new HashMap<>();
            values.put("ip", ip);
            values.put("userId", userId);
            values.put("date", new Date(now.getTime() - 300000));
            if (!blogVisitorDao.hasVisitors(values, session)){
                BlogVisitor blogVisitor = new BlogVisitor();
                blogVisitor.setCreateTime(now);
                blogVisitor.setIpAddress(ip);
                blogVisitor.setUserId(userId);
                session.save(blogVisitor);
            }
            session.flush();
            tran.commit();
            session.close();
        };
        new Thread(historyThread).start();
    }

    @Override
    public void addLoginHistory(String ip, int userId, String status) {
        new Thread(() -> {
            Date now = new Date();
            SessionFactory sessionFactory = (SessionFactory) ContextLoader.getCurrentWebApplicationContext().getBean("sessionFactory");
            Session session = sessionFactory.openSession();
            Transaction tran = session.beginTransaction();
            Map<String, Object> values = new HashMap<>();
            values.put("ip", ip);
            values.put("userId", userId);
            values.put("status", status);
            values.put("date", new Date(now.getTime() - 10000));
            if (!userLoginHistoryDao.hasLogin(values, session)){
                UserLoginHistory loginHistory = new UserLoginHistory();
                loginHistory.setLoginIp(ip);
                loginHistory.setLoginTime(now);
                loginHistory.setUserId(userId);
                loginHistory.setLoginStatus(status);
                session.save(loginHistory);
            }
            session.flush();
            tran.commit();
            session.close();
        }).start();
    }

}
