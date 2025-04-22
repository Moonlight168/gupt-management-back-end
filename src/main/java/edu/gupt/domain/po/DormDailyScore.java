package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DailyScore PO (Persistent Object) 类 - 表示宿舍每日评分
 */
@Data
@TableName("dorm_daily_score")
public class DormDailyScore {
    
    @TableId(type = IdType.AUTO)
    private Integer id;                 // 主键ID

    private Integer dormitoryId;        // 宿舍ID

    private String dormitoryName;       // 宿舍名称（冗余字段）

    private Double totalPoints;               // 每日评分

    private Long scoreId;            // 评分项名称

    private String grade;               // 评分等级

    private LocalDate date;             // 评分日期

    private String comment;             // 评论，可为空

    private LocalDateTime createTime;   // 创建时间

    private LocalDateTime updateTime;   // 更新时间

}
