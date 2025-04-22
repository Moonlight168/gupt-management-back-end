package edu.gupt.domain.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class StudentDTO implements Serializable {
    /**
     * 学号
     */
    @NotBlank(message = "学号不能为空")
    @Pattern(regexp = "^\\d{9}$", message = "学号必须是12位数字")
    private String studentId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Size(min = 2, max = 20, message = "姓名长度必须在2-20个字符之间")
    private String studentName;

    /**
     * 性别
     */
    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "^[男女]$", message = "性别只能是男或女")
    private String gender;

    /**
     * 入学日期
     */
    @NotNull(message = "入学日期不能为空")
    @Past(message = "入学日期必须是过去的时间")
    private Date registrationDate;

    /**
     * 学院ID
     */
    @NotNull(message = "学院不能为空")
    @Positive(message = "学院ID必须是正数")
    private Integer collegeId;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 专业ID
     */
    @NotNull(message = "专业不能为空")
    @Positive(message = "专业ID必须是正数")
    private Integer majorId;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 班级ID
     */
    @NotNull(message = "班级不能为空")
    @Positive(message = "班级ID必须是正数")
    private Integer classId;

    /**
     * 班级名称
     */
    private String className;
}