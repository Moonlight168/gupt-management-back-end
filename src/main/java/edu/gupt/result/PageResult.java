package edu.gupt.result;// PageResult.java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class PageResult{
    // Getters and Setters
    private List<?> records;       // 当前页的内容
    private Long total;          // 总条数
}
