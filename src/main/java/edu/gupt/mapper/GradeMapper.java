package edu.gupt.mapper;

import edu.gupt.domain.po.Grade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86130
* @description 针对表【grade(成绩表)】的数据库操作Mapper
* @createDate 2024-12-30 22:02:51
* @Entity edu.gupt.domain.po.Grade
*/
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {

    /**
     * 根据学生ID和学期获取成绩列表
     */
    List<Grade> selectBySemester(@Param("studentId") Long studentId, @Param("semester") String semester);

    /**
     * 获取学生的平均绩点
     */
    Double selectAvgGpa(@Param("studentId") Long studentId, @Param("semester") String semester);

    /**
     * 批量插入成绩
     */
    int batchInsert(@Param("grades") List<Grade> grades);
}



