package edu.gupt.controller;

import cn.hutool.core.bean.BeanUtil;
import edu.gupt.domain.dto.PageDTO;
import edu.gupt.domain.po.Activity;
import edu.gupt.domain.vo.ActivityVO;
import edu.gupt.domain.vo.InfoVO;
import edu.gupt.domain.vo.StarVO;
import edu.gupt.result.PageResult;
import edu.gupt.result.Result;
import edu.gupt.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    /**
     * 根据类型获取活动列表
     * @param typeId
     * @return
     */
    @GetMapping("/getActivityByTypeId/{typeId}")
    public Result<?> getActivityByTypeId(@PathVariable("typeId") Integer typeId) {
        try {
            List<Activity> newsList = activityService.getActivityByTypeId(typeId);
            List<?> voList;
            if (typeId == 1) {
                voList = newsList.stream().map(news -> BeanUtil.copyProperties(news, StarVO.class)).toList();
            } else {
                voList = newsList.stream().map(news -> BeanUtil.copyProperties(news, InfoVO.class)).toList();
            }
            return Result.success(voList);
        } catch (Exception e) {
            return Result.fail("Fetch type is " + typeId + "  activity failed:" + e.getCause());
        }
    }

    /**
     * 根据id获取活动详情
     * @param id
     * @return
     */
    @GetMapping("/getActivityById")
    public Result<ActivityVO> getActivityById(@RequestParam("id") Long id) {
        ActivityVO activityVO = BeanUtil.copyProperties(activityService.getById(id), ActivityVO.class);
        return Result.success(activityVO);
    }

    /**
     * 获取所有活动
     * @return
     */
    @RequestMapping("/list")
    public Result<List<ActivityVO>> getActivityList(){
        List<Activity> activityList = activityService.getActivityList();
        List<ActivityVO> activityVOList = BeanUtil.copyToList(activityList, ActivityVO.class);
        return Result.success(activityVOList);
    }

    /**
     * 分页获取活动
     * @param pageDTO
     * @return
     */
    @PostMapping("/getActivityPage")
    public Result<PageResult> getNewsPageByTypeId(@RequestBody PageDTO pageDTO) {
        // 参数校验
        try {
            PageResult pageResult = activityService.getActivityPage(pageDTO);
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.fail("Fetch activity failed: " + e.getCause());
        }
    }
}
