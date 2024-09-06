package com.mz.hat.dao;

import com.mz.hat.vo.CategoryVo;
import com.mz.hat.vo.RegionVo;
import com.mz.hat.vo.TouristAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<CategoryVo> list();

    List<TouristAttrVo> category_page(@Param("category_name") String category_name);
}
