package edu.gupt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gupt.domain.po.Club;
import edu.gupt.service.ClubService;
import edu.gupt.mapper.ClubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86130
* @description 针对表【clubs(社团信息表)】的数据库操作Service实现
* @createDate 2024-12-24 22:06:45
*/
@Service
public class ClubServiceImpl extends ServiceImpl<ClubMapper, Club>
    implements ClubService {

    @Autowired
    private ClubMapper clubMapper;

    @Override
    public List<Club> getClubList() {
        return lambdaQuery().orderByDesc(Club::getCreatedAt).list();
    }


}




