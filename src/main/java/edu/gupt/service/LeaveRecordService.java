package edu.gupt.service;

import edu.gupt.domain.dto.LeaveRequestDTO;
import edu.gupt.domain.po.LeaveRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86130
* @description 针对表【leave_record】的数据库操作Service
* @createDate 2024-12-30 21:06:44
*/
public interface LeaveRecordService extends IService<LeaveRecord> {

    List<LeaveRecord> getCurrentStudentLeaveRecords();

    void submitLeave(LeaveRequestDTO leaveRequestDTO);

    LeaveRecord getLeaveRecordById(Long id);

    boolean cancelLeaveRecord(Long id);

    boolean updateLeaveRecord(LeaveRequestDTO leaveRequestDTO);
}
