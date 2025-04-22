package edu.gupt.domain.details;

import edu.gupt.domain.enums.UserType;
import edu.gupt.domain.po.Student;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author gupt
 * @date 2023/05/08
 */
@Builder
public class StudentDetails implements UserDetails {
    private final Student student;

    public StudentDetails(Student student) {
        this.student = student;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        // 从学生对象中获取角色信息，并创建权限集合
        return Collections.singletonList(new SimpleGrantedAuthority(student.getRole()));
    }

    public Long getId() {
        return student.getId();
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        return student.getStudentId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public UserType getUserType() {
        return UserType.STUDENT;
    }
}