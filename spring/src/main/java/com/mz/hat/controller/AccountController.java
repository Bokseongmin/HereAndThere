package com.mz.hat.controller;

import com.mz.hat.service.RegionService;
import com.mz.hat.service.UserService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.annotation.MSPSession;
import com.mz.hat.support.interceptor.UserRole;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.RegionVo;
import com.mz.hat.vo.TouristAttrVo;
import com.mz.hat.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@MSP
@RequestMapping("/account")
@RestController
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegionService regionService;

    @GetMapping("/sign/up")
    public ModelAndView get_signUp(Model model) {
        List<RegionVo> regionVos = regionService.list();
        model.addAttribute("regions", regionVos);
        return new ModelAndView("account/sign/up");
    }

    @PostMapping("/sign/up")
    public ResponseEntity<MspResult> post_signUp(@RequestBody UserVo userVo) {
        MspResult mspResult;

        int affectRow = userService.signUp(userVo);

        if (affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, userVo);
        } else {
            mspResult = MspUtil.makeResult("400", "이미 등록되어 있습니다.", userVo);
        }
        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/sign/")
    public ResponseEntity<MspResult> get_sign(HttpSession httpSession) {
        MspResult result;

        if (httpSession.getAttribute("user") != null) {
            result = MspUtil.makeResult("400", "이미 로그인 되어 있습니다.", null);
        } else {
            result = MspUtil.makeResult("401", "로그인이 필요합니다.", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/sign/email_check")
    public ResponseEntity<MspResult> get_email(@RequestBody UserVo userVo) {
        MspResult result;

        int affectRow = userService.email_check(userVo.getUser_email());

        if (affectRow > 0) {
            result = MspUtil.makeResult("400", "잘못된 요청입니다.", null);
        } else {
            result = MspUtil.makeResult(MspStatus.OK, null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/sign/in")
    public ModelAndView get_signIn() {
        return new ModelAndView("account/sign/in");
    }

    @PostMapping("/sign/in")
    public ResponseEntity<MspResult> post_signIn(@RequestBody UserVo userVo, HttpSession httpSession) {
        MspResult result;
        if (!StringUtils.hasText(userVo.getUser_email())) {
            result = MspUtil.makeResult("400", "아이디 또는 비밀번호 확인해 주세요.", null);
        } else {
            UserVo signVo = userService.signIn(userVo);
            if(signVo != null){
                httpSession.setAttribute("user", signVo);
                result = MspUtil.makeResult(MspStatus.OK, null);
            } else {
                result = MspUtil.makeResult("401", "등록된 사용자가 없습니다.", null);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @MSPSession(role = UserRole.USER)
    @GetMapping("/sign/out")
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
            result = MspUtil.makeResult("401", "올바르지 않은 세션입니다.", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/sign/profile")
    public ModelAndView edit() {
        return new ModelAndView("account/profile");
    }

    @PostMapping("/sign/profile/update/img")
    public ResponseEntity<MspResult> update_img(@RequestParam("file") MultipartFile file, HttpSession httpSession) {
        MspResult result;
        UserVo userVo = (UserVo) httpSession.getAttribute("user");
        int affectRow = userService.uploadUserProfileImg(file, userVo.getId());
        if (userVo == null) {
            result = MspUtil.makeResult("401", "사용자가 존재하지 않습니다.", null);
        } else {
            if (affectRow > 0) {
                userVo = userService.info(userVo.getId());
                httpSession.setAttribute("user", userVo);
                result = MspUtil.makeResult(MspStatus.OK, null);
            } else {
                result = MspUtil.makeResult("404", "요청된 파일이 없습니다.", null);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/sign/profile/update")
    public ResponseEntity<MspResult> update_user_name(@RequestBody Map<String, String> data, HttpSession httpSession) {
        MspResult result;
        UserVo userVo = (UserVo) httpSession.getAttribute("user");
        int affectRow = userService.updateUserProfile(data, userVo);
        if (affectRow > 0) {
            result = MspUtil.makeResult(MspStatus.OK, null);
        } else {
            result = MspUtil.makeResult("401", "올바르지 않은 세션입니다.", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
