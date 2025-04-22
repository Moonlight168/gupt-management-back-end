package edu.gupt.domain.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class LeaveRecordVO {
    /**
     * 请假记录ID
     */
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
     * 请假类型（事假/病假）
     */
    private String type;

    /**
     * 请假原因
     */
    private String reason;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 请假状态（待审批/已批准/已拒绝）
     */
    private String status;

    /**
     * 审批人ID
     */
    private Long approveBy;

    /**
     * 审批人姓名（冗余字段）
     */
    private String approverName;

    /**
     * 审批时间
     */
    private LocalDateTime approveTime;

    /**
     * 审批意见
     */
    private String approveComment;

    /**
     * 请假课程列表
     */
    private List<LeaveCourseVO> courses;

    /**
     * 附件URL列表
     */
    private List<String> attachments;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

@Data
class LeaveCourseVO {
    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 上课时间
     */
    private String courseTime;

    /**
     * 上课地点
     */
    private String location;
}