package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName activity
 */
@Data
public class Activity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private Date eventDate;

    /**
     * 
     */
    private String photoUrl;

    /**
     * 
     */
    private Integer typeId;

    /**
     * 
     */
    private String location;

    /**
     * 
     */
    private String author;

    /**
     * 
     */
    private Date date;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}