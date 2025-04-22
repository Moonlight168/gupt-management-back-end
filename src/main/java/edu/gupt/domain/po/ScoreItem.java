package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;


import java.time.LocalDateTime;

@Data
@TableName("score_item")
public class ScoreItem {

    private Long itemId;
    private Long scoreId;
    private String name;
    private char grade;
    private int points;
    private String remark;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
