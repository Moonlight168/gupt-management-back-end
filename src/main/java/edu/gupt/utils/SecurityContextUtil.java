package edu.gupt.utils;

import edu.gupt.domain.details.StudentDetails;
import edu.gupt.domain.enums.UserType;
import edu.gupt.domain.po.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static cn.hutool.core.lang.Console.log;

@Slf4j
public class SecurityContextUtil {
    /**
     * 存储当前用户信息到securityContextHolder
     *
     * @param authentication 当前用户的认证信息
     */
    public static void storeAuthentication(Authentication authentication) {
        if (SecurityContextHolder.getContext() != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else SecurityContextHolder.createEmptyContext().setAuthentication(authentication);
    }

    public static UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 检查是否已认证
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // 检查 principal 是否是 UserDetails 实例
            if (principal instanceof UserDetails) {
                return (UserDetails) principal;
            }
        }
        // 如果没有认证或者 principal 不是 UserDetails，返回 null 或抛出异常
        return null;
    }


    /**
     * 获取当前登录用户
     */
    public static UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("用户未登录");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof StudentDetails) {
            return (StudentDetails) principal;
        } else {
            log.warn("未知的用户类型: {}", principal.getClass());
            throw new IllegalStateException("未知的用户类型: " + principal.getClass());
        }
    }


    /**
     * 获取当前登录用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails user = getCurrentUser(); // 获取当前用户

        if (user instanceof LoginUser) {
            return ((LoginUser) user).getId();
        } else if (user instanceof StudentDetails) {
            return ((StudentDetails) user).getId();
        } else {
            throw new IllegalStateException("未知的用户类型: " + user.getClass());
        }
    }


    /**
     * 获取当前登录用户类型
     */
    public static UserType getCurrentUserType() {
        UserDetails user = getCurrentUser(); // 获取当前用户

        if (user instanceof LoginUser) {
            return ((LoginUser) user).getUserType();
        } else if (user instanceof StudentDetails) {
            return ((StudentDetails) user).getUserType();
        } else {
            throw new IllegalStateException("未知的用户类型: " + user.getClass());
        }
    }

    /**
     * 判断当前用户是否是学生
     */
    public static boolean isStudent() {
        return UserType.STUDENT.equals(getCurrentUserType());
    }

    /**
     * 判断当前用户是否是教师
     */
    public static boolean isTeacher() {
        return UserType.TEACHER.equals(getCurrentUserType());
    }

    /**
     * 判断当前用户是否是管理员
     */
    public static boolean isAdmin() {
        return UserType.ADMIN.equals(getCurrentUserType());
    }

    /**
     * 获取学生ID（仅当前用户为学生时可用）
     */
    public static Long getStudentId() {
        if (!isStudent()) {
            throw new RuntimeException("当前用户不是学生");
        }
        return getCurrentUserId();
    }

    /**
     * 获取教师ID（仅当前用户为教师时可用）
     */
    public static Long getTeacherId() {
        if (!isTeacher()) {
            throw new RuntimeException("当前用户不是教师");
        }
        return getCurrentUserId();
    }
}
