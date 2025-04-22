package edu.gupt.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class StudentLoginDTO implements Serializable {
    /**
     * ID默认为账号名
     */
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = "^\\d{9}$", message = "账号必须是9位学号")
    private String account;

    /**
     * 密码默认为身份证号码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^\\d{17}[0-9X]$", message = "密码格式不正确")
    private String password;
}