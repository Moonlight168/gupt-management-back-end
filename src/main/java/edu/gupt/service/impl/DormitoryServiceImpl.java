package edu.gupt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gupt.domain.dto.DormDailyScoreDTO;
import edu.gupt.domain.po.*;
import edu.gupt.domain.vo.*;
import edu.gupt.mapper.*;
import edu.gupt.service.DormitoryService;
import edu.gupt.utils.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 86130
 * @description 针对表【dormitory】的数据库操作Service实现
 * @createDate 2025-01-31 15:02:44
 */
@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory>
        implements DormitoryService {

    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Autowired
    private DormitoryNoticeMapper dormitoryNoticeMapper;

    @Autowired
    private DormitoryScoreMapper dormitoryScoreMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private DormDailyScoreMapper dormDailyScoreMapper;

    @Autowired
    private ScoreItemMapper scoreItemMapper;

    @Override
    public List<DormitoryScore> getDormitoryRankings() {
        //获取本月所有宿舍的当前评分，按照当前评分降序排序，并返回前五名
        String currentMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        List<DormitoryScore> scoreList = dormitoryScoreMapper.selectList(
                new QueryWrapper<DormitoryScore>()
                        .eq("month", currentMonth)
                        .orderByDesc("score")
        );

        // 存储前五名的宿舍分数数据
        List<DormitoryScore> topScores = new ArrayList<>();

        if (!scoreList.isEmpty()) {
            // 先将第一名的宿舍添加进来
            topScores.add(scoreList.get(0));

            // 处理并列情况和限制排名为前五名
            for (int i = 1; i < scoreList.size(); i++) {
                // 如果当前分数和上一条记录的分数相同，则认为并列，加入当前记录
                if (scoreList.get(i).getScore() == scoreList.get(i - 1).getScore()) {
                    topScores.add(scoreList.get(i));
                }
                // 如果当前分数和上一条记录不同，且当前排名已经超过前五名，就停止
                else if (topScores.size() >= 5) {
                    break;
                } else {
                    // 否则，当前分数与上一条不同，加入当前记录
                    topScores.add(scoreList.get(i));
                }
            }
        }

        return topScores;
    }

    @Override
    public List<List<DormitoryScore>> getDormitoryWinners() {
        // 获取上个月月份，格式化为 yyyy-MM 字符串
        String lastMonth = LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // 查询上月的宿舍评分数据，假设 score_date 字段格式为 yyyy-MM 字符串
        List<DormitoryScore> scoreList = dormitoryScoreMapper.selectList(
                new QueryWrapper<DormitoryScore>()
                        .eq("month", lastMonth)
                        .orderByDesc("score")
        );

        // 存储前三名（可能有多个并列名次）
        List<List<DormitoryScore>> topScores = new ArrayList<>();
        int rank = 1;//当前名次
        double lastScore = scoreList.get(0).getScore();  // 用于存储上一个评分，用于判断是否并列

        List<DormitoryScore> info = new ArrayList<>();
        // 遍历所有查询结果，确保排名并列
        int i = 0;
        for (DormitoryScore score : scoreList) {
            //记录当前分数的宿舍信息
            // 如果当前分数与上一条记录不同，且已经收集到前三名，就停止遍历
            i++;
            if (score.getScore() != lastScore) {
                // 更新 lastScore
                lastScore = score.getScore();
                if (rank++ > 3) break;
                else {
                    topScores.add(info);
                    info = new ArrayList<>();
                    info.add(score);
                    if (i==scoreList.size())topScores.add(info);
                }
            } else {
                info.add(score);
            }
        }
        return topScores;
    }

    @Override
    public Dormitory getDormInfo() {
        // 获取当前登录用户id
        return getDormitory();
    }

    /**
     * 获取当前登录用户的宿舍信息
     * @return 宿舍信息
     */
    private Dormitory getDormitory() {
        Long userId = SecurityContextUtil.getCurrentUserId();
        String userIdStr = userId.toString();
        return lambdaQuery().like(Dormitory::getMembersId, "," + userIdStr + ",")
                .or().like(Dormitory::getMembersId, userIdStr)
                .or().like(Dormitory::getMembersId, "," + userIdStr)
                .or().eq(Dormitory::getMembersId, userIdStr).one();
    }

    /**
     * 获取最近五个月的月份列表
     * @param currentMonth 当前月份
     * @return 最近五个月的月份
     */
    private List<String> getLastFiveMonths(String currentMonth) {
        List<String> lastFiveMonths = new ArrayList<>();

        // 当前月份的年和月
        int year = Integer.parseInt(currentMonth.split("-")[0]);
        int month = Integer.parseInt(currentMonth.split("-")[1]);

        // 获取最近五个月
        for (int i = 0; i < 5; i++) {
            // 计算每个月份
            month--;
            if (month == 0) {
                month = 12;
                year--;
            }
            lastFiveMonths.add(String.format("%d-%02d", year, month)); // 格式化为 "yyyy-MM"
        }

        return lastFiveMonths;
    }

    @Override
    public List<DormitoryScore> getRecentScores() {
        // 查询最近五月的宿舍评分
        Dormitory dormitory = getDormitory();
        if (dormitory != null) {
            String currentMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            // 获取最近五个月的月份列表
            List<String> lastFiveMonths = getLastFiveMonths(currentMonth);
            // 查询最近五个月的宿舍评分，按日期排序
            return dormitoryScoreMapper.selectList(
                    new QueryWrapper<DormitoryScore>()
                            .eq("dormitory_id", dormitory.getId())
                            .in("month", lastFiveMonths) // 查询最近五个月的数据
                            .orderByDesc("month")
            );
        }
        return null;
    }


    @Override
    public DormDailyScoreDTO getDailyScores() {
        // 查询每日评分
        Long dormId = getDormitory().getId();
        //获取今日的日期
        LocalDate today = LocalDate.now();
        DormDailyScoreDTO dormDailyScoreDTO = new DormDailyScoreDTO();
        if (dormId != null) {
            DormDailyScore dailyScore = dormDailyScoreMapper.selectOne(
                    new QueryWrapper<DormDailyScore>()
                            .eq("dormitory_id", dormId)
                            .eq("date", today));
            BeanUtil.copyProperties(dailyScore, dormDailyScoreDTO);
            dormDailyScoreDTO.setItems(new ArrayList<>());
            if (dailyScore != null) {
                scoreItemMapper.selectList(
                        new QueryWrapper<ScoreItem>()
                                .eq("score_id", dailyScore.getScoreId())
                ).forEach(item -> {
                    DormDailyScoreDTO.ScoreItemDTO itemDTO = new DormDailyScoreDTO.ScoreItemDTO();
                    BeanUtil.copyProperties(item, itemDTO);
                    dormDailyScoreDTO.getItems().add(itemDTO);
                });
            }
            return dormDailyScoreDTO;
        }
        return null;
    }

    @Override
    public List<DormitoryNotice> getNotices() {
        // 查询宿舍公告并按照时间排序，获取前三条
        return dormitoryNoticeMapper.selectList(
                new QueryWrapper<DormitoryNotice>()
                        .orderByDesc("date")  // 按日期降序排序
                        .last("LIMIT 3")      // 限制返回的记录数为3
        );
    }
}


