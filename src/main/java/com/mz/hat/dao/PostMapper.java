package com.mz.hat.dao;

import com.mz.hat.vo.PostVo;
import javafx.geometry.Pos;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<PostVo> list();
    Integer write(PostVo postVo);

    Integer modify(PostVo postVo);

    Integer delete(PostVo postVo);
}
