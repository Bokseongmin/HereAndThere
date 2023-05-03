package com.mz.hat.controller;

import com.mz.hat.service.PostService;
import com.mz.hat.support.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.result.MspResult;
import com.mz.hat.support.result.MspStatus;
import com.mz.hat.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@MSP
@Slf4j
@RequestMapping("/post")
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/list")
    public ResponseEntity<MspResult> get_list() {
        MspResult mspResult;
        List<PostVo> postVos = postService.list();

        int postVosSize = postVos.size();

        if(postVosSize > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, postVos);
        } else {
            mspResult = MspUtil.makeResult("8888", "등록된 게시글이 없습니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @PostMapping("/write")
    public ResponseEntity<MspResult> post_write(@RequestBody PostVo postVo) {
        MspResult mspResult;

        int affectRow = postService.write(postVo);

        if(affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, postVo);
        } else {
            mspResult = MspUtil.makeResult("9999", "중복된 글입니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<MspResult> post_modify(@PathVariable("id") String id ,@RequestBody PostVo postVo) {
        MspResult mspResult;

        postVo.setId(Integer.parseInt(id));
        int affectRow = postService.modify(postVo);
        if(affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, postVo);
        } else {
            mspResult = MspUtil.makeResult("4444", "존재하지 않는 글입니다.", postVo);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MspResult> del_delete(@PathVariable("id") String id) {
        MspResult mspResult;

        PostVo vo = new PostVo();
        vo.setId(Integer.parseInt(id));
        int affectRow = postService.delete(vo);

        if(affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, vo);
        } else {
            mspResult = MspUtil.makeResult("4444", "존재하지 않는 글입니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }
}
