package edu.gupt.service.impl;

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gupt.domain.dto.LeaveRequestDTO;
import edu.gupt.domain.po.LeaveRecord;
import edu.gupt.service.LeaveRecordService;
import edu.gupt.mapper.LeaveRecordMapper;
import edu.gupt.utils.SecurityContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 86130
* @description 针对表【leave_record】的数据库操作Service实现
* @createDate 2024-12-30 21:06:44
*/
@Service
public class LeaveRecordServiceImpl extends ServiceImpl<LeaveRecordMapper, LeaveRecord>
    implements LeaveRecordService{
    @Autowired
    private LeaveRecordMapper leaveRecordMapper;

    @Override
    public List<LeaveRecord> getCurrentStudentLeaveRecords() {
        Long userId = SecurityContextUtil.getCurrentUserId();
        return this.lambdaQuery().eq(LeaveRecord::getUserId, userId).list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitLeave(LeaveRequestDTO leaveRequestDTO) {
        try {
            // 创建请假记录
            LeaveRecord leaveRecord = new LeaveRecord();
            BeanUtils.copyProperties(leaveRequestDTO, leaveRecord);
            leaveRecord.setUserId(SecurityContextUtil.getCurrentUserId());
            leaveRecord.setStatus("待审批");
            leaveRecord.setCreateTime(LocalDateTime.now());
            leaveRecord.setUpdateTime(LocalDateTime.now());
            // 插入请假记录
            leaveRecordMapper.insert(leaveRecord);
        } catch (Exception e) {
            throw new ServiceException("提交请假申请失败", e);
        }
    }

    @Override
    public LeaveRecord getLeaveRecordById(Long id) {
        return this.getById(id);
    }

    @Override
    public boolean cancelLeaveRecord(Long id) {
        return this.lambdaUpdate().eq(LeaveRecord::getId, id).set(LeaveRecord::getStatus, "已取消").update();
    }

    @Override
    public boolean updateLeaveRecord(LeaveRequestDTO leaveRequestDTO) {
        LeaveRecord leaveRecord = new LeaveRecord();
        BeanUtils.copyProperties(leaveRequestDTO, leaveRecord);
        return this.updateById(leaveRecord);
    }
}




