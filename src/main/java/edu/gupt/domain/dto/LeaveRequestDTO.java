package edu.gupt.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

@Data
public class LeaveRequestDTO {

    /**
     * 请假记录ID（可选）
     */
    private Long id;

    /**
     * 请假类型（事假/病假）
     */
    @NotBlank(message = "请假类型不能为空")
    private String type;

    /**
     * 请假原因
     */
    @NotBlank(message = "请假原因不能为空")
    @Size(min = 10, max = 500, message = "请假原因长度必须在10-500字之间")
    private String reason;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    @Future(message = "开始时间必须是将来时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    @Future(message = "结束时间必须是将来时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime endTime;

    /**
     * 请假课程ID列表
     */
    @NotEmpty(message = "请选择请假课程")
    private List<Long> courseIds;

    /**
     * 附件URL列表（可选）
     */
    private List<String> attachments;

    /**
     * 校验：开始时间必须早于结束时间
     */
    @AssertTrue(message = "开始时间必须早于结束时间")
    private boolean isTimeValid() {
        return startTime == null || endTime == null || startTime.isBefore(endTime);
    }

    /**
     * 校验：请假时长不能超过 14 天（精确计算）
     */
    @AssertTrue(message = "请假时长不能超过14天")
    private boolean isDurationValid() {
        if (startTime == null || endTime == null) {
            return true; // 让其他校验去检查 null 问题
        }
        long hours = Duration.between(startTime, endTime).toHours(); // 计算总小时数
        return hours / 24.0 <= 14; // 确保精确天数不超过 14 天
    }
}
