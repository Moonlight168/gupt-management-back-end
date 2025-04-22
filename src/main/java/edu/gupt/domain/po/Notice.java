package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;

@Data
public class Notice{
    @TableId
    private Integer id;

    private String title;

    private String content;

    @TableField("image")
    private String photoUrl;

    private String author;

    private Date date;

    private Integer typeId;
}