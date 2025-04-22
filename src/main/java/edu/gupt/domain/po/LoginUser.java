package edu.gupt.domain.po;

import edu.gupt.domain.enums.UserType;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
public class LoginUser implements UserDetails {
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型
     */
    private UserType userType;

    /**
     * 用户角色
     */
    private String role;
    
    /**
     * 权限列表
     */
    private Collection<? extends GrantedAuthority> authorities;

    // 构造方法
    public LoginUser(Long id, String username, String password, UserType userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.role = userType.getRole();
        // 设置权限
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + userType.getRole()));
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

}
