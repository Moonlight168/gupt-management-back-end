package edu.gupt.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class NewsVO implements Serializable {
    private Integer id;

    private String title;

    private String image;

    private String content;

    private String author;

    private Date date;

    private Integer typeId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
