package edu.gupt.service;

import edu.gupt.domain.dto.PageDTO;
import edu.gupt.domain.po.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.gupt.result.PageResult;

import java.util.List;

/**
* @author 86130
* @description 针对表【Notice】的数据库操作Service
* @createDate 2024-08-28 15:06:53
*/
public interface NoticeService extends IService<Notice> {
    List<List<Notice>> getNoticeList();

    List<Notice> getNoticeListByTypeId(Integer typeId);

    PageResult getNoticePageByTypeId(PageDTO pageDTO);
}
