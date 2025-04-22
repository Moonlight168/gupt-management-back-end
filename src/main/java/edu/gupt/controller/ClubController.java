package edu.gupt.controller;


import cn.hutool.core.bean.BeanUtil;
import edu.gupt.domain.po.Club;
import edu.gupt.domain.vo.ClubVO;
import edu.gupt.result.Result;
import edu.gupt.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    /**
     * 获取社团列表
     * @return 社团列表
     */
    @GetMapping("/list")
    public Result<List<ClubVO>> getClubList() {
        List<Club> clubList = clubService.getClubList();
        List<ClubVO> clubVOList = BeanUtil.copyToList(clubList, ClubVO.class);
        if (clubVOList == null) {
            return Result.fail("社团不存在或数据未找到");
        }
        return Result.success(clubVOList);
    }

    /**
     * 获取社团信息
     * @param id 社团ID
     * @return 社团介绍信息
     */
    @GetMapping()
    public Result<ClubVO> getClubById(@RequestParam("id") Long id) {
        ClubVO clubVO = BeanUtil.copyProperties(clubService.getById(id), ClubVO.class);
        if (clubVO == null) {
            return Result.fail("社团不存在或数据未找到");
        }
        return Result.success(clubVO);
    }
}
