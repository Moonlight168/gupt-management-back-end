package edu.gupt.service;

import edu.gupt.domain.dto.DormDailyScoreDTO;
import edu.gupt.domain.po.DormDailyScore;
import edu.gupt.domain.po.Dormitory;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.gupt.domain.po.DormitoryNotice;
import edu.gupt.domain.po.DormitoryScore;
import edu.gupt.domain.vo.*;

import java.util.List;

/**
* @author 86130
* @description 针对表【dormitory】的数据库操作Service
* @createDate 2025-01-31 15:02:44
*/
public interface DormitoryService extends IService<Dormitory> {

    List<DormitoryScore> getDormitoryRankings();

    List<List<DormitoryScore>> getDormitoryWinners();

    // 获取宿舍信息
    Dormitory getDormInfo();

    // 获取最近评分
    List<DormitoryScore> getRecentScores();

    // 获取每日评分
    DormDailyScoreDTO getDailyScores();

    // 获取公告信息
    List<DormitoryNotice> getNotices();
}
