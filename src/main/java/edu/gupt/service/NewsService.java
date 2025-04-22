package edu.gupt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.gupt.domain.dto.PageDTO;
import edu.gupt.domain.po.News;
import edu.gupt.result.PageResult;

import java.util.List;

/**
* @author 86130
* @description 针对表【News】的数据库操作Service
* @createDate 2024-08-28 15:06:53
*/
public interface NewsService extends IService<News> {
    /**
     * 根据类型id获取新闻列表 返回5个数据
     * @param type
     * @return
     */
    List<News> getNewsByTypeId(Integer type);

    /**
     * 获取所有新闻类型列表 每种5条
     * @return
     */
    List<List<News>> getNewsList();

    /**
     * 根据类型id获取新闻列表 分页
     * @param pageDTO
     * @return
     */
    PageResult getNewsPageByTypeId(PageDTO pageDTO);
}
