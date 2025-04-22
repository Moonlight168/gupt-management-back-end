package edu.gupt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WinnerVO {
    private List<String> championRoom;
    private int championScore;
    private List<String> runnerUpRoom;
    private int runnerUpScore;
    private List<String> thirdRoom;
    private int thirdScore;
}