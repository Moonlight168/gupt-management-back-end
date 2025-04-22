package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("grade")
public class Grade {
    /**
     * 成绩ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 学生姓名（冗余字段）
     */
    private String studentName;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程名称（冗余字段）
     */
    private String courseName;

    /**
     * 成绩分数
     */
    private Double score;

    /**
     * 学分
     */
    private Double credit;

    /**
     * 学期（格式：2023-2024-1）
     */
    private String semester;

    /**
     * 学年（格式：2023-2024）
     */
    private String academicYear;

    /**
     * 学期（1-第一学期，2-第二学期）
     */
    private Integer term;

    /**
     * 绩点（0.00-5.00）
     */
    private Double gradePoint;

    /**
     * 考试时间
     */
    private LocalDateTime examTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}