package edu.gupt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gupt.domain.constants.InfoType;
import edu.gupt.domain.dto.PageDTO;
import edu.gupt.domain.po.News;
import edu.gupt.mapper.NewsMapper;
import edu.gupt.result.PageResult;
import edu.gupt.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86130
 * @description 针对表【News】的数据库操作Service实现
 * @createDate 2024-08-28 15:06:53
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News>
        implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> getNewsByTypeId(Integer typeId) {
        typeId = InfoType.getInfoType(typeId);
        if (typeId == 1)
            return this.lambdaQuery()
                    .apply("type_id % 2 = 1")
                    .last("limit 5")
                    .orderByDesc(News::getDate).list();
        else return this.lambdaQuery().eq(News::getTypeId, typeId)
                .or().eq(News::getTypeId, typeId + 1)
                .last("limit 5")
                .orderByDesc(News::getDate).list();
    }

    @Override
    public List<List<News>> getNewsList() {
        // 定义新闻类型数组
        int[] types = {InfoType.NEWS, InfoType.CULTURE, InfoType.TRAINING, InfoType.ENROLLMENT_AND_EMPLOYMENT};
        // 初始化新闻列表来存储最终结果
        List<List<News>> allNews = new ArrayList<>();
        for (int type : types) {
            List<News> newsForType = lambdaQuery()
                    .select(News::getId, News::getTitle, News::getDate)
                    .eq(News::getTypeId, type + 1)
                    .or().eq(News::getTypeId, type)
                    .last("LIMIT 5")
                    .orderByDesc(News::getDate)
                    .list();
            // 将结果添加到总列表中
            allNews.add(newsForType);
        }
        return allNews;
    }

    @Override
    public PageResult getNewsPageByTypeId(PageDTO pageDTO) {
        // 创建分页对象
        Page<News> page = new Page<>(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        int typeId = InfoType.getInfoType(pageDTO.getTypeId());
        // 使用 MyBatis Plus 的分页查询
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.eq("type_id", typeId).or()
                .eq("type_id",typeId+1).orderByDesc("date");
        IPage<News> newsPage = newsMapper.selectPage(page, wrapper);
        // 封装结果
        return new PageResult(newsPage.getRecords(), newsPage.getTotal());
    }
}




