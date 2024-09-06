package com.mz.hat.dao;

import com.mz.hat.vo.TagVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper {
    Integer verify(TagVo tagVo);

    // 관광지 별 태그 조회
    List<TagVo> findByTour_id(@Param("tour_id") int tour_id);
}
