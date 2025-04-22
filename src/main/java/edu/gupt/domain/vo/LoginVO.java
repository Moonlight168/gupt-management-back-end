package edu.gupt.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVO {
    private String token;
    private String userId;
    private String userName;
    private String role;
    private String registrationDate;
}
