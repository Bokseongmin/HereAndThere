package com.mz.hat.controller;

import com.mz.hat.support.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.result.MspResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@MSP
@Slf4j
@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<MspResult> home() {
        return null;
    }

    @GetMapping("/get/reviews")
    public ResponseEntity<MspResult> get_reviews() {
        return null;
    }

    @GetMapping("/get/posts")
    public ResponseEntity<MspResult> get_posts() {
        return null;
    }

    @GetMapping("/error")
    public ResponseEntity<MspResult> error() {
        MspResult mspResult = MspUtil.makeResult("4444", "세션이 존재하지 않습니다.", null);

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }
}
