package edu.gupt.controller;

import cn.hutool.core.bean.BeanUtil;
import edu.gupt.domain.dto.LeaveRequestDTO;
import edu.gupt.domain.po.LeaveRecord;
import edu.gupt.domain.vo.LeaveRecordVO;
import edu.gupt.result.Result;
import edu.gupt.service.LeaveRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
@Slf4j
public class LeaveController {
    @Autowired
    private LeaveRecordService leaveService;

    /**
     * 获取当前学生的请假记录
     * @return 请假记录列表
     */
    @GetMapping("/records")
    public Result<List<LeaveRecordVO>> getLeaveRecords() {
        try {
            List<LeaveRecord> records = leaveService.getCurrentStudentLeaveRecords();
            List<LeaveRecordVO> recordVOList = records.stream()
                .map(record -> BeanUtil.copyProperties(record, LeaveRecordVO.class))
                .toList();
            return Result.success(recordVOList);
        } catch (Exception e) {
            log.error("获取请假记录失败: {}", e.getMessage(), e);
            return Result.fail("获取请假记录失败");
        }
    }

    /**
     * 提交请假申请
     * @param request 请假申请信息
     * @return 提交成功
     */
    @PostMapping("/submit")
    public Result<Void> submitLeave(@RequestBody LeaveRequestDTO request) {
        try {
            leaveService.submitLeave(request);
            return Result.success();
        } catch (Exception e) {
            log.error("提交请假申请失败: {}", e.getMessage(), e);
            return Result.fail("提交请假申请失败");
        }
    }

    /**
     * 获取请假详情
     * @param id 请假记录ID
     * @return 请假详情
     */
    @GetMapping("/detail/{id}")
    public Result<LeaveRecordVO> getLeaveDetail(@PathVariable("id") Long id) {
        try {
            LeaveRecord record = leaveService.getLeaveRecordById(id);
            if (record == null) {
                return Result.fail("请假记录不存在");
            }
            LeaveRecordVO recordVO = BeanUtil.copyProperties(record, LeaveRecordVO.class);
            return Result.success(recordVO);
        } catch (Exception e) {
            log.error("获取请假详情失败: {}", e.getMessage(), e);
            return Result.fail("获取请假详情失败");
        }
    }

    /**
     * 取消请假申请
     * @param id 请假记录ID
     * @return 取消成功
     */
    @DeleteMapping("/cancel/{id}")
    public Result<Void> cancelLeaveRequest(@PathVariable("id") Long id) {
        try {
            boolean cancelled = leaveService.cancelLeaveRecord(id);
            if (!cancelled) {
                return Result.fail("取消请假申请失败");
            }
            return Result.success();
        } catch (Exception e) {
            log.error("取消请假申请失败: {}", e.getMessage(), e);
            return Result.fail("取消请假申请失败");
        }
    }

    /**
     * 更新请假详情
     * @param leaveRequestDTO 请假详情
     * @return 更新成功
     */
    @PutMapping("/update")
    public Result<Void> updateLeaveDetail(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        try {
            boolean updated = leaveService.updateLeaveRecord(leaveRequestDTO);
            if (!updated) {
                return Result.fail("更新请假详情失败");
            }
            return Result.success();
        } catch (Exception e) {
            log.error("更新请假详情失败: {}", e.getMessage(), e);
            return Result.fail("更新请假详情失败");
        }
    }
}