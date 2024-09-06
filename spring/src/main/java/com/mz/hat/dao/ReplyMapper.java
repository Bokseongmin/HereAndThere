package com.mz.hat.dao;

import com.mz.hat.vo.ReplyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

    // 관광지 댓글 작성, 조회
    int insertTourist_reply(ReplyVo replyVo);
    List<ReplyVo> selectTourist_replys(@Param("entity_id") int entity_id);

    // 코스 댓글 작성, 조회
    int insertCourse_reply(ReplyVo replyVo);
    List<ReplyVo> selectCourse_replys(@Param("entity_id") int entity_id);

    // 동행 댓글 작성, 조회
    int insertTogether_reply(ReplyVo replyVo);
    List<ReplyVo> selectTogether_replys(@Param("entity_id") int entity_id);
}
