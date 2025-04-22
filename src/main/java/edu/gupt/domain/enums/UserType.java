package edu.gupt.domain.enums;

/**
 * 用户类型枚举
 */
public enum UserType {
    STUDENT("STUDENT"),
    TEACHER("TEACHER"),
    ADMIN("ADMIN");

    private final String role;

    UserType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}