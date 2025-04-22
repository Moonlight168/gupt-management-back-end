package edu.gupt.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ClubVO {
    private Long id;          // 社团ID
    private String name;      // 社团名称
    private String logoUrl;   // 社团Logo的URL
    private String description; // 社团简介
    private String presidentName; // 社长姓名
    private String num; // 社团人数
    private Date createdAt;// 社团创建时间
}
