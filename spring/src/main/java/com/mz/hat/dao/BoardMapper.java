package com.mz.hat.dao;

import com.mz.hat.vo.BoardVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVo> list(@Param("offset") int offset, @Param("page_size") int page_size);

    BoardVo detail(int id);

    Integer write(BoardVo boardVo);

    Integer modify(BoardVo boardVo);

    Integer delete(BoardVo boardVo);
}
