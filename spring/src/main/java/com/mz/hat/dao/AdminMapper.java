package com.mz.hat.dao;

import com.mz.hat.vo.TouristAttrVo;
import com.mz.hat.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<UserVo> get_user_list();

    List<TouristAttrVo> get_tour_list(@Param("offset") int offset, @Param("limit") int limit);

    int cntTourist();
    TouristAttrVo get_tour_detail(int id);

    Integer tour_modify(TouristAttrVo touristAttrVo);
    Integer add_tour_list(TouristAttrVo touristAttrVo);

    Integer get_cnt(String category_name);

    Integer all_cnt();
}
