package cn.nnnight.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 权限工具类
 */
public class AuthUtil {

    public static Integer getUserId() {
        try {
            CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return customUser.getId();
        } catch (Exception e) {
            return 0;
        }
    }
}
