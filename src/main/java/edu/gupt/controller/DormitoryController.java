package edu.gupt.controller;

import cn.hutool.core.bean.BeanUtil;
import edu.gupt.domain.dto.DormDailyScoreDTO;
import edu.gupt.domain.po.DormDailyScore;
import edu.gupt.domain.po.Dormitory;
import edu.gupt.domain.po.DormitoryNotice;
import edu.gupt.domain.po.DormitoryScore;
import edu.gupt.domain.vo.*;
import edu.gupt.result.Result;
import edu.gupt.service.DormitoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/dormitory")
@Slf4j
public class DormitoryController {

    @Autowired
    private DormitoryService dormitoryService;

    /**
     * 获取用户所在宿舍信息
     *
     * @return
     */
    @GetMapping("/info")
    public Result<DormitoryInfoVO> getDormInfo() {
        try {
            Dormitory dormitory = dormitoryService.getDormInfo();
            DormitoryInfoVO dormitoryInfoVO = new DormitoryInfoVO();
            BeanUtil.copyProperties(dormitory, dormitoryInfoVO);
            List<DormMemberVO> dormitoryMembers = new ArrayList<>();
            // 获取成员姓名和学号，按逗号分割
            String membersName = dormitory.getMembersName();
            String membersStudentId = dormitory.getMembersStudentId();

            // 分割成员姓名和学号
            String[] nameArray = membersName.split(",");
            String[] studentIdArray = membersStudentId.split(",");

            // 确保两个数组长度一致
            int length = Math.min(nameArray.length, studentIdArray.length);

            // 遍历并创建 DormMemberVO 对象
            for (int i = 0; i < length; i++) {
                String name = nameArray[i].trim();
                String studentId = studentIdArray[i].trim();
                DormMemberVO member;
                if (i == 0) {
                     member = new DormMemberVO(name, studentId, "宿舍长");
                } else {
                     member = new DormMemberVO(name, studentId, "成员");
                }
                dormitoryMembers.add(member);
            }
            dormitoryInfoVO.setMembers(dormitoryMembers);
            return Result.success(dormitoryInfoVO);
        } catch (Exception e) {
            log.error("Error occurred while retrieving dormitory information: {}", e.getMessage(), e);
            return Result.fail("Failed to fetch dormitory information: " + e.getMessage());
        }
    }

    /**
     * 获取本月前五宿舍排名
     *
     * @return
     */
    @GetMapping("/rankings")
    public Result<List<DormitoryRankingVO>> getDormitoryRankings() {
        try {
            List<DormitoryScore> rankings = dormitoryService.getDormitoryRankings();
            List<DormitoryRankingVO> re = rankings.stream().map(score -> {
                DormitoryRankingVO rankingVO = new DormitoryRankingVO();
                rankingVO.setRoom(score.getBuilding() + "栋" + score.getRoom());
                rankingVO.setScore(score.getScore());
                return rankingVO;
            }).toList();
            return Result.success(re);
        } catch (Exception e) {
            log.error("Error occurred while retrieving dormitory rankings: {}", e.getMessage(), e);
            return Result.fail("Dormitory rankings fetching failed: " + e.getMessage());
        }
    }

    /**
     * 获取上月前三获奖者
     *
     * @return
     */
    @GetMapping("/winners")
    public Result<WinnerVO> getDormitoryWinners() {
        try {
            List<List<DormitoryScore>> dormitoryWinners = dormitoryService.getDormitoryWinners();
            WinnerVO winners = new WinnerVO(
                    dormitoryWinners.get(0).stream().map(score -> score.getBuilding() + "栋" + score.getRoom()).toList(),
                    dormitoryWinners.get(0).get(0).getScore(),
                    dormitoryWinners.get(1).stream().map(score -> score.getBuilding() + "栋" + score.getRoom()).toList(),
                    dormitoryWinners.get(1).get(0).getScore(),
                    dormitoryWinners.get(2).stream().map(score -> score.getBuilding() + "栋" + score.getRoom()).toList(),
                    dormitoryWinners.get(2).get(0).getScore()
            );
            return Result.success(winners);
        } catch (Exception e) {
            log.error("Error occurred while retrieving dormitory winners: {}", e.getMessage(), e);
            return Result.fail("Dormitory winners fetching failed: " + e.getMessage());
        }
    }


    /**
     * 获取近五月的评分记录
     *
     * @return List<ScoreRecord>
     */
    @GetMapping("/recent-scores")
    public Result<List<DormitoryScoreVO>> getRecentScores() {
        try {
            List<DormitoryScore> scores = dormitoryService.getRecentScores();
            List<DormitoryScoreVO> scoresVO = scores.stream().map(score -> {
                DormitoryScoreVO scoreVO = new DormitoryScoreVO();
                scoreVO.setDormitoryId(score.getDormitoryId());
                scoreVO.setPoints(score.getScore());
                scoreVO.setMonth(score.getMonth());
                return scoreVO;
            }).toList();
            return Result.success(scoresVO);
        } catch (Exception e) {
            log.error("Error occurred while retrieving recent scores: {}", e.getMessage(), e);
            return Result.fail("Failed to fetch recent scores: " + e.getMessage());
        }
    }


    /**
     * 获取每日评分
     *
     * @return List<DailyScoreVO>
     */
    @GetMapping("/daily-scores")
    public Result<DailyScoreVO> getDailyScores() {
        try {
            DormDailyScoreDTO dailyScoreDTO = dormitoryService.getDailyScores();
            DailyScoreVO dailyScoresVO = new DailyScoreVO();
            BeanUtil.copyProperties(dailyScoreDTO, dailyScoresVO);
            dailyScoresVO.setItems(
                    dailyScoreDTO.getItems().stream().map(item -> {
                        DailyScoreVO.Item itemVO = new DailyScoreVO.Item();
                        BeanUtil.copyProperties(item, itemVO);
                        return itemVO;
                    }).toList()
            );
            return Result.success(dailyScoresVO);
        } catch (Exception e) {
            log.error("Error occurred while retrieving daily scores: {}", e.getMessage(), e);
            return Result.fail("Failed to fetch daily scores: " + e.getMessage());
        }
    }

    /**
     * 获取公告信息
     *
     * @return List<DormitoryNoticeVO>
     */
    @GetMapping("/notices")
    public Result<List<DormitoryNoticeVO>> getNotices() {
        try {
            List<DormitoryNotice> notices = dormitoryService.getNotices();
            List<DormitoryNoticeVO> noticeVOS = notices.stream().map(notice -> {
                DormitoryNoticeVO noticeVO = new DormitoryNoticeVO();
                noticeVO.setNoticeId(notice.getId());
                noticeVO.setContent(notice.getContent());
                noticeVO.setDate(notice.getDate());
                return noticeVO;
            }).toList();
            return Result.success(noticeVOS);
        } catch (Exception e) {
            log.error("Error occurred while retrieving notices: {}", e.getMessage(), e);
            return Result.fail("Failed to fetch notices: " + e.getMessage());
        }
    }
}