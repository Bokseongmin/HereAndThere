package com.mz.hat.dao;

import com.mz.hat.vo.CoordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoordMapper {

    // 코스 좌표 등록
    int insertCoords_course(CoordVo coordVo);

    // 동행 좌표 등록
    int insertCoords_together(CoordVo coordVo);

    // 코스 좌표 조회
    List<CoordVo> findByCourse_id(@Param("entity_id") int entity_id);

    // 동행 좌표 조회
    List<CoordVo> findByTogether_id(@Param("entity_id") int entity_id);
}
