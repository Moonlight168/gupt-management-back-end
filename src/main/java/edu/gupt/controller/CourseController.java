package edu.gupt.controller;

import cn.hutool.core.bean.BeanUtil;
import edu.gupt.domain.po.Course;
import edu.gupt.domain.po.Grade;
import edu.gupt.domain.vo.CourseVO;
import edu.gupt.domain.vo.GradeVO;
import edu.gupt.result.Result;
import edu.gupt.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {
    @Autowired
    private CourseService courseService;

    /**
     * 获取课程表
     * @param week 周数
     * @return 课程表
     */
    @GetMapping("/schedule")
    public Result<List<CourseVO>> getSchedule(@RequestParam("week") Integer week) {
        try {
            List<Course> courseList = courseService.getScheduleByWeek(week);
            List<CourseVO> courseVOList = courseList.stream()
                    .map(course -> BeanUtil.copyProperties(course, CourseVO.class))
                    .toList();
            return Result.success(courseVOList);
        } catch (Exception e) {
            log.error("获取课程表失败: {}", e.getMessage(), e);
            return Result.fail("获取课程表失败");
        }
    }

    /**
     * 获取成绩
     * @param semester
     * @return
     */
    @GetMapping("/grades")
    public Result<List<GradeVO>> getGrades(@RequestParam("semester") String semester) {
        try {
            List<Grade> gradeList = courseService.getGradesBySemester(semester);
            List<GradeVO> gradeVOList = gradeList.stream()
                    .map(grade -> BeanUtil.copyProperties(grade, GradeVO.class))
                    .toList();
            return Result.success(gradeVOList);
        } catch (Exception e) {
            log.error("获取成绩失败: {}", e.getMessage(), e);
            return Result.fail("获取成绩失败");
        }
    }
}