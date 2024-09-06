package com.mz.hat.service;

import com.mz.hat.dao.ReplyMapper;
import com.mz.hat.vo.ReplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    @Autowired
    private ReplyMapper replyMapper;

    /*
    관광지 댓글 작성, 조회
     */
    public int insertTourist_reply(int tour_id, int user_id, String content) {
        ReplyVo replyVo = new ReplyVo();
        replyVo.setEntity_id(tour_id);
        replyVo.setUser_id(user_id);
        replyVo.setContent(content);
        return replyMapper.insertTourist_reply(replyVo);
    }
    public List<ReplyVo> selectTourist_replys(int tour_id) {
        return replyMapper.selectTourist_replys(tour_id);
    }

    /*
    코스 댓글 작성, 조회
     */
    public int insertCourse_reply(int course_id, int user_id, String content) {
        ReplyVo replyVo = new ReplyVo();
        replyVo.setEntity_id(course_id);
        replyVo.setUser_id(user_id);
        replyVo.setContent(content);
        return replyMapper.insertCourse_reply(replyVo);
    }
    public List<ReplyVo> selectCourse_replys(int tour_id) {
        return replyMapper.selectCourse_replys(tour_id);
    }

    /*
    동행 댓글 작성, 조회
     */
    public int insertTogether_reply(int together_id, int user_id, String content) {
        ReplyVo replyVo = new ReplyVo();
        replyVo.setEntity_id(together_id);
        replyVo.setUser_id(user_id);
        replyVo.setContent(content);
        return replyMapper.insertTourist_reply(replyVo);
    }
    public List<ReplyVo> selectTogether_replys(int tour_id) {
        return replyMapper.selectTourist_replys(tour_id);
    }
}
