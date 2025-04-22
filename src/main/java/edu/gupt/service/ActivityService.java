package edu.gupt.service;

import edu.gupt.domain.dto.PageDTO;
import edu.gupt.domain.po.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.gupt.domain.po.News;
import edu.gupt.domain.vo.ActivityVO;
import edu.gupt.result.PageResult;
import edu.gupt.result.Result;

import java.util.List;

/**
* @author 86130
* @description 针对表【Activities】的数据库操作Service
* @createDate 2024-11-16 16:58:02
*/
public interface ActivityService extends IService<Activity> {

    List<Activity> getActivityByTypeId(Integer typeId);

    List<Activity> getActivityList();

    PageResult getActivityPage(PageDTO pageDTO);
}
