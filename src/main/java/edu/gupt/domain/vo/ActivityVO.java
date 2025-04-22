package edu.gupt.domain.vo;

import lombok.Data;

@Data
public class ActivityVO {
    private Integer id;
    private String title;
    private String content;
    private String photoUrl;
    private String location;
    private String author;
    private String date;
}
