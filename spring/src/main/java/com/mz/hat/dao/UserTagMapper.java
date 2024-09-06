package com.mz.hat.dao;

import com.mz.hat.vo.TagVo;
import com.mz.hat.vo.UserTagVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserTagMapper {
    List<UserTagVo> info(UserTagVo userTagVo);
    List<UserTagVo> get(int tour_id);
    Integer add(UserTagVo userTagVo);
    Integer delete(UserTagVo userTagVo);
}
