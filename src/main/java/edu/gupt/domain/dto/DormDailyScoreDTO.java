package edu.gupt.domain.dto;

import edu.gupt.domain.po.ScoreItem;
import lombok.Data;

import java.util.List;

@Data
public class DormDailyScoreDTO {
    private Long id;
    private String date;
    private String grade;
    private List<ScoreItemDTO> items;
    private Double totalPoints;

    @Data
    public static class ScoreItemDTO {
        private String name;
        private String grade;
        private Double points;
        private String imageUrl;
        private String remark;
    }
}
