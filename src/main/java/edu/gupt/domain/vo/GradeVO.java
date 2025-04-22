package edu.gupt.domain.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GradeVO {
    /**
     * 成绩ID
     */
    private Long id;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程名称（冗余字段）
     */
    private String courseName;

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 学生姓名（冗余字段）
     */
    private String studentName;

    /**
     * 成绩分数
     */
    private Double score;

    /**
     * 学分
     */
    private Double credit;

    /**
     * 学期
     */
    private String semester;

    /**
     * 绩点
     */
    private Double gradePoint;

    /**
     * 考试时间
     */
    private LocalDateTime examTime;

    /**
     * 考试性质（正考/补考/重修）
     */
    private String examType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}