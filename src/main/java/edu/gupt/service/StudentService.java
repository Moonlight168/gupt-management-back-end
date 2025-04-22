package edu.gupt.service;

import edu.gupt.domain.po.Student;
import com.baomidou.mybatisplus.extension.service.IService;

public interface StudentService extends IService<Student> {
    Student studentLogin(Student student);
}
