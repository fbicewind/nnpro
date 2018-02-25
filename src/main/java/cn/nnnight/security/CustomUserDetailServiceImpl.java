package cn.nnnight.security;

import cn.nnnight.dao.UserDao;
import cn.nnnight.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUniqueResult("username", username);
        CustomUser customUser = new CustomUser(user.getUsername(), user.getPassword(), user.getState() == 1, true, true, true, getAuthorities(user.getRole()));
        customUser.setId(user.getId());
        customUser.setNickname(user.getNickname());
        customUser.setAvatar(user.getAvatar());
        return customUser;
    }

    private Collection<GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = new ArrayList<>();
        // 通用权限
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        // 管理权限
        if (role == 2) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        // 超管权限
        if (role == 3) {
            authList.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
        }
        return authList;
    }
}
