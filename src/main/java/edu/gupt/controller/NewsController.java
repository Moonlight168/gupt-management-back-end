package edu.gupt.controller;

import cn.hutool.core.bean.BeanUtil;
import edu.gupt.domain.dto.PageDTO;
import edu.gupt.domain.po.News;
import edu.gupt.domain.vo.InfoVO;
import edu.gupt.domain.vo.NewsVO;
import edu.gupt.domain.vo.StarVO;
import edu.gupt.result.PageResult;
import edu.gupt.result.Result;
import edu.gupt.service.NewsService;
import edu.gupt.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/news")
@Slf4j
public class NewsController {
    @Autowired
    private NewsService newsService;

    /**
     * 获取新闻信息
     * @return
     */
    @GetMapping("/info")
    public Result<List<List<InfoVO>>> getInfo() {
        try {
            List<List<News>> allNews = newsService.getNewsList();
            List<List<InfoVO>> re = new ArrayList<>();
            allNews.forEach(newsList ->{
                List<InfoVO> infoVOList = newsList.stream().map(news -> BeanUtil.copyProperties(news, InfoVO.class)).toList();
                re.add(infoVOList);
            });
            return Result.success(re);
        } catch (Exception e) {
            log.error("Error occurred while retrieving news: {}", e.getCause(),e);
            return Result.fail("News list fetching failed:" + e.getCause());
        }
    }

    /**
     * 获取新闻根据Id
     * @param id
     * @return
     */
    @GetMapping("")
    public Result<NewsVO> getNewsById(@RequestParam("id") Long id) {
        try {
            News news = newsService.getById(id);
            NewsVO newsVO = BeanUtil.copyProperties(news, NewsVO.class);
            return Result.success(newsVO);
        } catch (Exception e) {
            return Result.fail("The article does not exist:" + e.getCause());
        }
    }

    /**
     * 获取新闻根据类型
     * @param typeId
     * @return
     */
    @GetMapping("/getNewsByTypeId/{typeId}")
    public Result<List<?>> getNewsByTypeId(@PathVariable("typeId") Integer typeId) {
        try {
            List<News> newsList = newsService.getNewsByTypeId(typeId);
            List<?> voList;
            if (typeId == 1) {
                voList = newsList.stream().map(news -> BeanUtil.copyProperties(news, StarVO.class)).toList();
            } else {
                voList = newsList.stream().map(news -> BeanUtil.copyProperties(news, InfoVO.class)).toList();
            }
            return Result.success(voList);
        } catch (Exception e) {
            return Result.fail("Fetch type is " + typeId + "  news failed:" + e.getCause());
        }
    }

    /**
     * 获取新闻分页根据类型
     * @param pageDTO
     * @return
     */
    @PostMapping("/getNewsPageByTypeId")
    public Result<PageResult> getNewsPageByTypeId(@RequestBody PageDTO pageDTO) {
        // 参数校验
        if (ValidateUtil.isPageValid(pageDTO)) {
            return Result.fail("Invalid arguments");
        }
        try {
            PageResult pageResult = newsService.getNewsPageByTypeId(pageDTO);
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.fail("Fetch type is " + pageDTO.getTypeId() + " article failed: " + e.getCause());
        }
    }
}
