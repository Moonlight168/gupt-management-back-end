package edu.gupt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseVO {
    private int id;
    private String name;
    private String teacher;
    private String location;
    private int weekDay;
    private int startSlot;
    private int endSlot;
    private List<Integer> weeks;
}