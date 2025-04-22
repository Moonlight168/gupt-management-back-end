package edu.gupt.domain.vo;

import edu.gupt.domain.po.Student;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DormitoryInfoVO {
    private Long id; // 宿舍ID
    private String room; // 宿舍编号
    private String building; // 楼栋名称
    private Integer currentOccupancy; // 当前入住人数
    private List<DormMemberVO> members;
    private Integer currentScore;//本月分数 月初清空为零
}