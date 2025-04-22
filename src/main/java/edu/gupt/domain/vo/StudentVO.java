package edu.gupt.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class StudentVO implements Serializable {
    /**
     * 学号
     */
    private String studentId;

    /**
     * 姓名
     */
    private String studentName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 入学日期
     */
    private Date registrationDate;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 班级名称
     */
    private String className;

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

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
