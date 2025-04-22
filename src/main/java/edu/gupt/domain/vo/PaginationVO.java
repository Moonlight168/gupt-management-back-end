package edu.gupt.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PaginationVO implements Serializable {

    private String total;

    // 分页数据
    private List<?> data;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
