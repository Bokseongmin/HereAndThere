package com.mz.hat.dao;

import com.mz.hat.vo.TouristAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TouristAttrMapper {

    // 전체 조회
    List<TouristAttrVo> findAll();

    // 전체 조회 (페이징)
    List<TouristAttrVo> list(@Param("limit") int limit, @Param("offset") int offset);

    // 태그별 조회 (페이징)
    List<TouristAttrVo> tag_list(@Param("tag_id") int tag_id, @Param("limit") int limit, @Param("offset") int offset);

    // 검색 목록
    List<TouristAttrVo> search_touristAttr(@Param("word") String word);

    // 지역별 조회 (페이징)
    List<TouristAttrVo> findByRegion(@Param("region") String region, @Param("limit") int limit, @Param("offset") int offset);
    TouristAttrVo search_name(String name);
    
    // 상세 조회
    TouristAttrVo detail(int id);
    
    //조회수 업데이트
    int incrementView_cnt(int id);
}
