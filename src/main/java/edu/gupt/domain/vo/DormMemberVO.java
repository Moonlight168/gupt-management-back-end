package edu.gupt.domain.vo;

import lombok.Data;

@Data
public class DormMemberVO {
    private String name;
    private String studentId;
    private String role;

    public DormMemberVO(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
    }

    public DormMemberVO(String name, String studentId, String role) {
        this.name = name;
        this.studentId = studentId;
        this.role = role;
    }
}
