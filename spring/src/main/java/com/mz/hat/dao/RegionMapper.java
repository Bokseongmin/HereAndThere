package com.mz.hat.dao;

import com.mz.hat.vo.RegionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RegionMapper {
    List<RegionVo> list();
}
