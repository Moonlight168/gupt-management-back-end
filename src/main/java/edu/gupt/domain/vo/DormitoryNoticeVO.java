package edu.gupt.domain.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DormitoryNoticeVO {
    private Long noticeId;  // 公告ID
    private String content;  // 公告内容
    private LocalDateTime date;  // 公告发布时间
}
