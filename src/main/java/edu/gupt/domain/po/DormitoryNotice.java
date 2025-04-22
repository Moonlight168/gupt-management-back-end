package edu.gupt.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DormitoryNotice {

    /**
     * 公告ID
     */
    private Long id;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告发布时间
     */
    private LocalDateTime date;

}
