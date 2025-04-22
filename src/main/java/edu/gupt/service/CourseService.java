package edu.gupt.service;

import edu.gupt.domain.po.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.gupt.domain.po.Grade;
import edu.gupt.domain.po.LeaveRecord;
import edu.gupt.domain.vo.GradeVO;

import java.util.List;

/**
* @author 86130
* @description 针对表【course】的数据库操作Service
* @createDate 2024-12-30 19:53:49
*/
public interface CourseService extends IService<Course> {

    List<Course> getScheduleByWeek(Integer week);

    List<Grade> getGradesBySemester(String semester);

}
