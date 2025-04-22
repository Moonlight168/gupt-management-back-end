package edu.gupt.domain.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dormitory {
    private Long id;
    private String building;
    private String room;
    private String membersName;
    private String membersId;//成员id
    private String membersStudentId;
    private String headOfDormId;
    private String headOfDormName;
    private Integer currentScore;//本月分数 月初清空为零
}