package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请假记录实体类
 * @TableName leave_record
 */
@TableName(value = "leave_record")
@Data
public class LeaveRecord implements Serializable {
    /**
     * 请假记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 学生ID
     */
    private Long userId;

    /**
     * 学生姓名
     */
    private String studentName;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}