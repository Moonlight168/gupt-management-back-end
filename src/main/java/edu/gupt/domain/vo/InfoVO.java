package edu.gupt.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class InfoVO implements Serializable {
    private Integer id;

    private String title;

    private Date date;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
