package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dormitory_score_month")
public class DormitoryScore {
    private Long id;
    private Long dormitoryId;
    private String building;
    private String room;
    private String month;
    private int score;
}