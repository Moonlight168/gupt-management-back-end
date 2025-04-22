package edu.gupt.domain.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DailyScoreVO {

    private Long id;
    /**
     * 日期
     */
    private LocalDate date;

    private String grade;

    /**
     * 评分项列表
     */
    private List<Item> items;

    /**
     * 总分数
     */
    private Double totalPoints;

    /**
     * 评分项内部类
     */
    @Data
    public static class Item {
        /**
         * 评分项名称
         */
        private String name;

        /**
         * 评分项等级
         */
        private String grade;

        /**
         * 评分项分数
         */
        private Double points;

        private String imageUrl;

        /**
         * 评分项备注
         */
        private String remark;
    }
}
