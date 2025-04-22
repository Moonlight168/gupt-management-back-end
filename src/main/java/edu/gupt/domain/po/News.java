package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import lombok.Data;

@Data
public class News{
    @TableId
    private Integer id;

    private String title;

    private String photoUrl;

    private String content;

    private String author;

    private Date date;

    private Integer typeId;
}