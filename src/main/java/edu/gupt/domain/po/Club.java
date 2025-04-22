package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 社团信息表
 * @TableName clubs
 */
@TableName(value ="club")
@Data
public class Club implements Serializable {
    /**
     * 社团ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 社团名称
     */
    private String name;

    /**
     * 社团简介
     */
    private String description;

    /**
     * 社团Logo图片URL
     */
    private String logoUrl;

    /**
     * 社长用户ID
     */
    private Long presidentId;

    /**
     * 社长姓名
     */
    private String presidentName;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 社团人数
     */
    private Integer num;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}