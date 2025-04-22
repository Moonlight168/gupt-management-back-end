package edu.gupt.service;

import edu.gupt.domain.po.Club;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86130
* @description 针对表【clubs(社团信息表)】的数据库操作Service
* @createDate 2024-12-24 22:06:45
*/
public interface ClubService extends IService<Club> {
    List<Club> getClubList();
}
