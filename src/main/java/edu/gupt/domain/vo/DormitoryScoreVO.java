package edu.gupt.domain.vo;

import lombok.Data;

@Data
public class DormitoryScoreVO {
    private Long dormitoryId;   // 宿舍ID
    private Integer points;  // 宿舍得分
    private String month;
}
