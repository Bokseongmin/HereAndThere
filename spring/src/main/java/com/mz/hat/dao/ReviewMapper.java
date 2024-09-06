package com.mz.hat.dao;

import com.mz.hat.vo.ReviewVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    List<ReviewVo> list();
    Integer write(ReviewVo reviewVo);

    ReviewVo detail(int id);

    Integer modify(ReviewVo reviewVo);

    Integer delete(ReviewVo reviewVo);
    List<ReviewVo> top10();
}
