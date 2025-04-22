package edu.gupt.domain.dto;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class PageDTO {
    /**
     * 类型ID
     */
    @NotNull(message = "类型ID不能为空")
    @Min(value = 1, message = "类型ID必须大于0")
    private Integer typeId;

    /**
     * 当前页码
     */
    @NotNull(message = "当前页码不能为空")
    @Min(value = 1, message = "页码必须大于0")
    private Integer currentPage;

    /**
     * 每页显示的条数
     */
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数必须大于0")
    @Max(value = 100, message = "每页条数不能超过100")
    private Integer pageSize;
}