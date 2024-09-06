package com.mz.hat.controller;

import com.mz.hat.service.ReplyService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.ReplyVo;
import com.mz.hat.vo.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Resource
    private ReplyService replyService;

    /*
    관광지 댓글 작성, 조회
     */
    @PostMapping("/tourist/{tour_id}")
    public ResponseEntity<MspResult> add_tourist_reply(@PathVariable int tour_id, @RequestBody Map<String, String> data, HttpSession session) {
        MspResult result;
        UserVo userVo = (UserVo) session.getAttribute("user");
        if (userVo != null) {
            int affectRow = replyService.insertTourist_reply(tour_id, userVo.getId(), data.get("content"));
            if (affectRow > 0) {
                result = MspUtil.makeResult(MspStatus.OK, null);
            } else {
                result = MspUtil.makeResult("403", "댓글을 등록하던 중 오류가 발생했습니다.", null);
            }
        } else {
            result = MspUtil.makeResult("401", "인증 되지 않은 사용자입니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/tourist/{tour_id}")
    public ResponseEntity<MspResult> get_tourist_replys(@PathVariable int tour_id) {
        MspResult result;
        List<ReplyVo> list = replyService.selectTourist_replys(tour_id);
        if (list != null) {
            result = MspUtil.makeResult(MspStatus.OK, list);
        } else {
            result = MspUtil.makeResult("404", "등록된 댓글이 없습니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    코스 댓글 작성, 조회
     */
    @PostMapping("/course/{course_id}")
    public ResponseEntity<MspResult> add_course_reply(@PathVariable int course_id, @RequestBody Map<String, String> data, HttpSession session) {
        MspResult result;
        UserVo userVo = (UserVo) session.getAttribute("user");
        if (userVo != null) {
            int affectRow = replyService.insertCourse_reply(course_id, userVo.getId(), data.get("content"));
            if (affectRow > 0) {
                result = MspUtil.makeResult(MspStatus.OK, null);
            } else {
                result = MspUtil.makeResult("403", "댓글을 등록하던 중 오류가 발생했습니다.", null);
            }
        } else {
            result = MspUtil.makeResult("401", "인증 되지 않은 사용자입니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/course/{course_id}")
    public ResponseEntity<MspResult> get_course_replys(@PathVariable int course_id) {
        MspResult result;
        List<ReplyVo> list = replyService.selectCourse_replys(course_id);
        if (list != null) {
            result = MspUtil.makeResult(MspStatus.OK, list);
        } else {
            result = MspUtil.makeResult("404", "등록된 댓글이 없습니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    동행 댓글 작성, 조회
     */
    @PostMapping("/together/{together_id}")
    public ResponseEntity<MspResult> add_together_reply(@PathVariable int together_id, @RequestBody Map<String, String> data, HttpSession session) {
        MspResult result;
        UserVo userVo = (UserVo) session.getAttribute("user");
        if (userVo != null) {
            int affectRow = replyService.insertTogether_reply(together_id, userVo.getId(), data.get("content"));
            if (affectRow > 0) {
                result = MspUtil.makeResult(MspStatus.OK, null);
            } else {
                result = MspUtil.makeResult("403", "댓글을 등록하던 중 오류가 발생했습니다.", null);
            }
        } else {
            result = MspUtil.makeResult("401", "인증 되지 않은 사용자입니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/together/{together_id}")
    public ResponseEntity<MspResult> get_together_replys(@PathVariable int together_id) {
        MspResult result;
        List<ReplyVo> list = replyService.selectTogether_replys(together_id);
        if (list != null) {
            result = MspUtil.makeResult(MspStatus.OK, list);
        } else {
            result = MspUtil.makeResult("404", "등록된 댓글이 없습니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
