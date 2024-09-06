package com.mz.hat.dao;

import com.mz.hat.vo.TogetherVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TogetherMapper {
    List<TogetherVo> list();

    Integer write(TogetherVo togetherVo);

    Integer write_route(TogetherVo togetherVo);

    TogetherVo detail(int id);

    TogetherVo info(TogetherVo togetherVo);

    Integer modify(TogetherVo togetherVo);

    Integer delete(TogetherVo togetherVo);
}
