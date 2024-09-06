package com.mz.hat.dao;

import com.mz.hat.vo.ImageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageMapper {
    void insert_image(ImageVo imagesVo);

    List<ImageVo> get_image(@Param("image_type") String image_type, @Param("ref_id") int ref_id);
}
