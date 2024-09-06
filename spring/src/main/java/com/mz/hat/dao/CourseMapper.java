package com.mz.hat.dao;

import com.mz.hat.vo.CourseVo;
import com.mz.hat.vo.RouteVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {

    // 코스 등록
    int insertCourse(CourseVo courseVo);

    // 코스 전체 조회
    List<CourseVo> findAll(@Param("offset") int offset, @Param("limit") int limit);

    // 코스 전체 갯수
    int course_cnt();

    // 코스 상세 조회
    CourseVo findById(@Param("id") int id);

    // 코스 수정
    int updateById(CourseVo coursevo);
}
