package com.mz.hat.controller;

import com.mz.hat.service.UserService;
import com.mz.hat.support.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.annotation.MSPSession;
import com.mz.hat.support.result.MspResult;
import com.mz.hat.support.result.MspStatus;
import com.mz.hat.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@MSP
@RequestMapping("/sign")
@RestController
public class SignController {

    @Autowired
    private UserService userService;

    @PostMapping("/up")
    public ResponseEntity<MspResult> post_signUp(@RequestBody UserVo userVo) {
        MspResult mspResult;

        int affectRow = userService.signUp(userVo);

        if(affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, userVo);
        } else {
            mspResult = MspUtil.makeResult("9999", "이미 등록되어 있습니다.", userVo);
        }
        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/in")
    public ResponseEntity<MspResult> get_signIn(HttpSession httpSession) {
        MspResult result;

        if (httpSession.getAttribute("user") != null) {
            result = MspUtil.makeResult("2222", "이미 로그인 되어 있습니다.", null);
        } else {
            result = MspUtil.makeResult("4444", "로그인이 필요합니다.", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/in")
    public ResponseEntity<MspResult> post_signIn(@RequestBody UserVo userVo, HttpSession httpSession) {
        MspResult result;

        UserVo user = userService.signIn(userVo);

        if (!StringUtils.hasText(user.getUser_email())) {
            result = MspUtil.makeResult("4444", "아이디 또는 비밀번호 확인해 주세요.", null);
        } else {
            httpSession.setAttribute("user", user);
            result = MspUtil.makeResult(MspStatus.OK, null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @MSPSession(role = 1)
    @GetMapping("/out")
    public ResponseEntity<MspResult> get_signOut(HttpSession httpSession) {
        httpSession.invalidate();
        MspResult mspResult = MspUtil.makeResult(MspStatus.OK, null);
        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/session")
    public ResponseEntity<MspResult> session(HttpSession httpSession) {
        MspResult result;

        if (httpSession.getAttribute("user") != null) {
            UserVo user = (UserVo) httpSession.getAttribute("user");
            result = MspUtil.makeResult(MspStatus.OK, user);
        } else {
            result = MspUtil.makeResult("4444", "로그인이 되어있지 않습니다.", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
