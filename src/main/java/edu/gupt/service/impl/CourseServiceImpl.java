package edu.gupt.service.impl;

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gupt.domain.po.Course;
import edu.gupt.domain.po.Grade;
import edu.gupt.mapper.CourseMapper;
import edu.gupt.mapper.GradeMapper;
import edu.gupt.mapper.LeaveRecordMapper;
import edu.gupt.service.CourseService;
import edu.gupt.utils.SecurityContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    private final CourseMapper courseMapper;
    private final GradeMapper gradeMapper;
    private final LeaveRecordMapper leaveRecordMapper;

    public CourseServiceImpl(CourseMapper courseMapper, GradeMapper gradeMapper, LeaveRecordMapper leaveRecordMapper) {
        this.courseMapper = courseMapper;
        this.gradeMapper = gradeMapper;
        this.leaveRecordMapper = leaveRecordMapper;
    }

    @Override
    public List<Course> getScheduleByWeek(Integer week) {
        try {
            // 使用 LambdaQueryWrapper 构建查询条件
            LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(Course::getWeeks, week)  // 周数包含当前周
                    .orderByAsc(Course::getWeekDay) // 按星期升序
                    .orderByAsc(Course::getStartSlot); // 按节次升序

            return this.list(wrapper);
        } catch (Exception e) {
            log.error("获取第{}周课程表失败: {}", week, e.getMessage(), e);
            throw new ServiceException("获取课程表失败");
        }
    }

    @Override
    public List<Grade> getGradesBySemester(String semester) {
        try {
            // 确保是学生身份
            if (!SecurityContextUtil.isStudent()) {
                throw new ServiceException("只有学生可以查看成绩");
            }

            Long id = SecurityContextUtil.getStudentId();

            LambdaQueryWrapper<Grade> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Grade::getId, id)
                    .eq(Grade::getSemester, semester)
                    .orderByDesc(Grade::getUpdateTime);

            return gradeMapper.selectList(wrapper);
        } catch (Exception e) {
            log.error("获取{}学期成绩失败: {}", semester, e.getMessage(), e);
            throw new ServiceException("获取成绩失败");
        }
    }
}