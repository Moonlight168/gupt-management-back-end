package edu.gupt.controller;

import cn.hutool.core.bean.BeanUtil;
import edu.gupt.domain.dto.PageDTO;
import edu.gupt.domain.po.Notice;
import edu.gupt.domain.vo.InfoVO;
import edu.gupt.domain.vo.NoticeVO;
import edu.gupt.result.PageResult;
import edu.gupt.result.Result;
import edu.gupt.service.NoticeService;
import edu.gupt.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notice")
@Slf4j
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    /**
     * 获取所有公告信息
     *
     * @return
     */
    @GetMapping("/info")
    public Result<List<List<InfoVO>>> getInfo() {
        try {
            List<List<Notice>> noticeList = noticeService.getNoticeList();
            List<List<InfoVO>> result = new ArrayList<>();
            noticeList.forEach(notices -> {
                List<InfoVO> infoVOList = notices.stream().map(notice -> BeanUtil.copyProperties(notice, InfoVO.class)).toList();
                result.add(infoVOList);
            });
            return Result.success(result);
        } catch (Exception e) {
            // 异常处理
            log.error("Error occurred while retrieving notices: {}", e.getCause(), e);
            return Result.fail("Failed to retrieve notices.");
        }
    }

    /**
     * 获取公告详情
     *
     * @param id
     * @return
     */
    @GetMapping("")
    public Result<NoticeVO> getNoticeById(@RequestParam("id") Long id) {
        try {
            Notice notice = noticeService.getById(id);
            NoticeVO noticeVO = new NoticeVO();
            BeanUtil.copyProperties(notice, noticeVO);
            return Result.success(noticeVO);
        } catch (Exception e) {
            return Result.fail("公告不存在:" + e.getCause());
        }
    }

    /**
     * 获取公告列表
     *
     * @param typeId
     * @return
     */
    @GetMapping("/getNoticeByTypeId/{typeId}")
    public Result<List<InfoVO>> getNewsByTypeId(@PathVariable("typeId") Integer typeId) {
        try {
            List<Notice> noticeList = noticeService.getNoticeListByTypeId(typeId);
            List<InfoVO> startVOList = noticeList.stream().map(notice -> BeanUtil.copyProperties(notice, InfoVO.class)).toList();
            return Result.success(startVOList);
        } catch (Exception e) {
            return Result.fail("Fetch type is " + typeId + " notice failed:" + e.getCause());
        }
    }

    /**
     * 获取公告分页
     *
     * @param pageDTO
     * @return
     */
    @PostMapping("/getNoticePageByTypeId")
    public Result<PageResult> getNewsPageByTypeId(@RequestBody PageDTO pageDTO) {
        // 参数验证
        if (ValidateUtil.isPageValid(pageDTO)) return Result.fail("invalid arguments");
        try {
            PageResult pageResult = noticeService.getNoticePageByTypeId(pageDTO);
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.fail("Fetch type is " + pageDTO.getTypeId() + " article failed: " + e.getCause());
        }
    }

}
