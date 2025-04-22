package edu.gupt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gupt.domain.constants.InfoType;
import edu.gupt.domain.dto.PageDTO;
import edu.gupt.domain.po.News;
import edu.gupt.domain.po.Notice;
import edu.gupt.result.PageResult;
import edu.gupt.service.NoticeService;
import edu.gupt.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86130
 * description  针对表【Notice】的数据库操作Service实现
 * createDate  2024-08-28 15:06:53
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
        implements NoticeService {

    private final NoticeMapper noticeMapper;

    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Override
    public List<List<Notice>> getNoticeList() {
        // 定义通知类型数组
        int[] types = {InfoType.OTHER, InfoType.OPEN_INFO};
        List<List<Notice>> allNotice = new ArrayList<>();
        for (int type : types) {
            List<Notice> noticeForType = lambdaQuery()
                    .select(Notice::getId, Notice::getTitle, Notice::getDate)
                    .eq(Notice::getTypeId, type)
                    .or().eq(Notice::getTypeId, type)
                    .last("LIMIT 5")
                    .orderByDesc(Notice::getDate)
                    .list();
            // 将结果添加到总列表中
            allNotice.add(noticeForType);
        }
        return allNotice;
    }

    @Override
    public List<Notice> getNoticeListByTypeId(Integer typeId) {
        typeId = InfoType.getInfoType(typeId);
        return this.lambdaQuery().eq(Notice::getTypeId, typeId)
                .last("limit 5")
                .orderByDesc(Notice::getDate).list();
    }

    @Override
    public PageResult getNoticePageByTypeId(PageDTO pageDTO) {
        // 创建分页对象
        Page<Notice> page = new Page<>(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        int typeId = InfoType.getInfoType(pageDTO.getTypeId());
        // 使用 MyBatis Plus 的分页查询
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (typeId == 8) {
            wrapper.orderByDesc("date");
        }else
            wrapper.eq("type_id", typeId).orderByDesc("date");
        IPage<Notice> noticePage = noticeMapper.selectPage(page, wrapper);
        // 封装结果
        return new PageResult(noticePage.getRecords(), noticePage.getTotal());

    }
}




