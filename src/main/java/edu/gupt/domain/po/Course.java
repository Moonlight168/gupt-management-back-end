package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName course
 */
@TableName(value ="course")
@Data
public class Course implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String teacherName;

    /**
     * 
     */
    private String location;

    /**
     * 
     */
    private Integer weekDay;

    /**
     * 
     */
    private Integer startSlot;

    /**
     * 
     */
    private Integer endSlot;

    /**
     * 
     */
    private String weeks;

    /**
     * 教师id
     */
    private Long teacherId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}