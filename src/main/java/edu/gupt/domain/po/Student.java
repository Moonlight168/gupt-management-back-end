package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Student{
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 学号
     */
    private String studentId;

    /**
     * 角色
     */
    private String role;

    /**
     * 姓名
     */
    private String studentName;

    /**
     * 身份证号
     */
    private String studentNumber;

    /**
     * 性别
     */
    private String gender;

    /**
     * 入学日期
     */
    private Date registrationDate;

    /**
     * 学院ID
     */
    private Integer collegeId;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 班级ID
     */
    private Integer classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 密码默认为学生身份证号码
     */
    private String password;

    /**
     * 专业ID
     */
    private Integer majorId;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 政治面貌
     */
    private Integer politicalstatus;

    /**
     * 职位
     */
    private String position;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 班主任
     */
    private String headteacher;

    /**
     * 辅导员
     */
    private String counselor;

    /**
     * 头像url
     */
    private String avatar;

}