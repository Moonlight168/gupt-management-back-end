package edu.gupt.controller;

import cn.hutool.core.bean.BeanUtil;
import edu.gupt.domain.details.StudentDetails;
import edu.gupt.domain.dto.StudentDTO;
import edu.gupt.domain.dto.StudentLoginDTO;
import edu.gupt.domain.po.Student;
import edu.gupt.domain.vo.LoginVO;
import edu.gupt.domain.vo.StudentVO;
import edu.gupt.result.Result;
import edu.gupt.service.StudentService;
import edu.gupt.utils.JwtTokenUtil;
import edu.gupt.utils.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 学生登录
     */
    @PostMapping("/login")
    public Result<LoginVO> studentLogin(@RequestBody StudentLoginDTO studentLoginDTO) {
        try {
            // 验证用户凭证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            studentLoginDTO.getAccount(), studentLoginDTO.getPassword()
                    )
            );

            // 如果认证成功，保存用户身份信息到 SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 获取返回的凭证里的用户详情
            StudentDetails studentDetail = (StudentDetails) authentication.getPrincipal();
            // 生成 JWT token
            String token = jwtTokenUtil.generateToken(studentDetail.getAuthorities(), studentDetail.getId());
            Student student = studentService.getById(studentDetail.getId());
            LoginVO loginVO = LoginVO.builder().token(token)
                    .role(student.getRole())
                    .userId(student.getStudentId())
                    .userName(student.getStudentName())
                    .registrationDate(String.valueOf(student.getRegistrationDate()))
                    .build();
            return Result.success(loginVO);
        } catch (Exception e) {
            // 记录错误日志
            log.error("登录失败: {}", e.getMessage(), e);
            return Result.fail("登录失败，请检查用户名和密码");
        }
    }


    /**
     * 学生信息修改
     */
    @PostMapping("/update")
    public void studentUpdate(@RequestBody StudentDTO studentDTO) {
        Student student = Student.builder().build();
        BeanUtil.copyProperties(studentDTO, student);
        studentService.updateById(student);
    }

    /**
     * 学生信息删除
     */
    @DeleteMapping
    public void studentDelete(@RequestBody String studentId) {
        Student student = Student.builder()
                .studentId(studentId)
                .build();
        studentService.removeById(student);
    }

    /**
     * 根据学生id查询学生信息
     */
    @GetMapping
    public Result<StudentVO> getStudentById() {
        StudentDetails studentDetails = (StudentDetails) SecurityContextUtil.getUserDetails();
        Student student = studentService.getById(studentDetails.getId());
        StudentVO studentVO = new StudentVO();
        BeanUtil.copyProperties(student, studentVO);
        return Result.success(studentVO);
    }
}
