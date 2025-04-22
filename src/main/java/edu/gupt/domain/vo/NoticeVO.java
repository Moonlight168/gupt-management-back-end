package edu.gupt.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class NoticeVO implements Serializable {
    private Integer id;

    private String title;

    private String photoUrl;

    private String content;

    private String author;

    private Date date;

    private Integer typeId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
