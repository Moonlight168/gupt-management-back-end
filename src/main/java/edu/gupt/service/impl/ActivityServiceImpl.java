package edu.gupt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gupt.domain.constants.InfoType;
import edu.gupt.domain.dto.PageDTO;
import edu.gupt.domain.po.Activity;
import edu.gupt.domain.po.News;
import edu.gupt.result.PageResult;
import edu.gupt.service.ActivityService;
import edu.gupt.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86130
 * @description 针对表【Activities】的数据库操作Service实现
 * @createDate 2024-11-16 16:58:02
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public List<Activity> getActivityByTypeId(Integer typeId) {
        return this.lambdaQuery().eq(Activity::getTypeId, typeId)
                .last("limit 5")
                .orderByDesc(Activity::getDate).list();
    }

    @Override
    public List<Activity> getActivityList() {
        return this.lambdaQuery().orderByDesc(Activity::getDate).last("limit 5").list();
    }

    @Override
    public PageResult getActivityPage(PageDTO pageDTO) {
        // 创建分页对象
        Page<Activity> page = new Page<>(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        // 使用 MyBatis Plus 的分页查询
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("date");
        IPage<Activity> newsPage = activityMapper.selectPage(page, wrapper);
        // 封装结果
        return new PageResult(newsPage.getRecords(), newsPage.getTotal());
    }
}




