package edu.gupt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gupt.domain.details.StudentDetails;
import edu.gupt.domain.po.Student;
import edu.gupt.mapper.StudentMapper;
import edu.gupt.service.StudentService;
import edu.gupt.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 86130
 * @description 针对表【students】的数据库操作Service实现
 * @createDate 2024-06-08 17:22:54
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService, UserService {
    public Student studentLogin(Student student) {
        //给当前角色赋予role
        student = lambdaQuery().eq(Student::getStudentId, student.getStudentId())
                .eq(Student::getPassword, student.getPassword()).one();
        //登陆失败
        return student;
    }

//    AuthenticationManager 会调用其内部的 UserDetailsService 实现，
//    我这里实现了 UserDetailsService 接口，并重写了loadByUsername方法
//    通常是通过 DaoAuthenticationProvider。这个过程涉及以下几个步骤：
//    DaoAuthenticationProvider 会调用 loadUserByUsername 方法
    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        // 分配角色信息
        Student student = lambdaQuery().eq(Student::getStudentId, studentId).one();
        if (student == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new StudentDetails(student);
    }
}




