package com.mz.hat.controller;

import com.mz.hat.entity.CoordEntity;
import com.mz.hat.service.CourseService;
import com.mz.hat.service.TouristAttrService;
import com.mz.hat.service.UserService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.CoordVo;
import com.mz.hat.vo.CourseVo;
import com.mz.hat.vo.TouristAttrVo;
import com.mz.hat.vo.UserVo;
import jdk.nashorn.internal.ir.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@MSP
@Slf4j
@RequestMapping("/course")
@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    @Value("${kakao.secret.map.key}")
    private String appKey;

    /*
    페이지 표시
     */
    @GetMapping()
    public ModelAndView home() {
        return new ModelAndView("/pages/course/list");
    }

    @GetMapping("/write")
    public ModelAndView write(Model model) {
        model.addAttribute("appKey", appKey);
        return new ModelAndView("/pages/course/write");
    }

    @GetMapping("/detail")
    public ModelAndView detail() {
        return new ModelAndView("/pages/course/detail");
    }

    @GetMapping("/modify")
    public ModelAndView modify() {
        return new ModelAndView("/pages/course/modify");
    }

    /*
    코스 및 경로 추가
     */
    @PostMapping("/add")
    public ResponseEntity<MspResult> add(@RequestBody CourseVo courseVo, HttpSession httpSession){
        MspResult result;
        UserVo userVo = (UserVo) httpSession.getAttribute("user");
        courseVo.setUser_id(userVo.getId());
        int affectRow = courseService.insertCourse(courseVo);

        if (affectRow > 0) {
            result = MspUtil.makeResult(MspStatus.OK, null);
        } else {
            result = MspUtil.makeResult("400", "등록 중 오류가 발생했습니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    코스 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<MspResult> list(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10")int size) {
        MspResult result;
        List<CourseVo> list = courseService.findAll(page, size);

        int affectRow = list.size();
        int tourist_cnt = courseService.course_cnt();
        int tourist_page = (int) Math.ceil((double) tourist_cnt / page);

        int start_page = Math.max(1, page - 5);
        int end_page = Math.min(tourist_page, page + 5);

        if (affectRow > 0) {
            Map<String, Object> res = new HashMap<>();
            res.put("course", list);
            res.put("total", tourist_cnt);
            res.put("current", page);
            res.put("page", tourist_page);
            res.put("start", start_page);
            res.put("end", end_page);
            result = MspUtil.makeResult(MspStatus.OK, res);
        } else {
            result = MspUtil.makeResult("400", "등록 중 오류가 발생했습니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    코스 목록
     */


    /*
    코스 상세 조회
     */
    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") int id, Model model) {
        CourseVo course = courseService.findById(id);
        UserVo userVo = userService.summary(course.getUser_id());
        model.addAttribute("course", course);
        model.addAttribute("course_user", userVo);
        model.addAttribute("appKey", appKey);
        return new ModelAndView("/pages/course/detail");
    }

    /*
    코스 수정
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<MspResult> update(@PathVariable("id") int id, @RequestBody CourseVo courseVo) {
        MspResult result;
        int affectRow = courseService.updateById(id, courseVo);

        if (affectRow > 0) {
            result = MspUtil.makeResult(MspStatus.OK, null);
        } else {
            result = MspUtil.makeResult("400", "업데이트 중 오류가 발생했습니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
