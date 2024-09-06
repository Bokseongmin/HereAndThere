package com.mz.hat.controller;

import com.mz.hat.service.RegionService;
import com.mz.hat.service.CourseService;
import com.mz.hat.service.TogetherService;
import com.mz.hat.service.UserService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@MSP
@Slf4j
@RequestMapping("/together")
@RestController
public class TogetherController {
    @Autowired
    private TogetherService togetherService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Value("${kakao.secret.map.key}")
    private String appKey;

    @GetMapping()
    public ModelAndView list() {
        return new ModelAndView("pages/together/list");
    }

    @GetMapping("/list/get")
    public ResponseEntity<MspResult> get_list() {
        MspResult mspResult;

        List<TogetherVo> togetherVos = togetherService.list();

        for(TogetherVo togetherVo: togetherVos) {
            togetherVo.setUser_name(userService.info(togetherVo.getUser_id()).getUser_name());
        }

        int togetherVosSize = togetherVos.size();

        if (togetherVosSize > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, togetherVos);
        } else {
            mspResult = MspUtil.makeResult("8888", "등록된 게시글이 없습니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/write")
    public ModelAndView get_write(Model model) {
        model.addAttribute("appKey", appKey);
//        List<RegionVo> regionVos = regionService.list();
//        model.addAttribute("regions", regionVos);
        return new ModelAndView("pages/together/write");
    }

    @PostMapping("/write")
    public ResponseEntity<MspResult> post_write(@RequestBody TogetherVo togetherVo) {
        MspResult result;

        int affectRow = togetherService.write(togetherVo);

        if (affectRow > 1) {
            result = MspUtil.makeResult(MspStatus.OK, togetherVo);
        } else {
            result = MspUtil.makeResult("400", "이미 등록되어 있습니다.", togetherVo);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/write/get/tourist/{word}")
    public ResponseEntity<MspResult> get_tourist(@PathVariable("word") String word) {
        MspResult result;

        List<TouristAttrVo> list = togetherService.touristAttrList(word);

        int affectRow = list.size();

        if (affectRow > 0) {
            result = MspUtil.makeResult(MspStatus.OK, list);
        } else {
            result = MspUtil.makeResult("404", "존재하지 않는 관광지입니다.", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable("id") String id, Model model) {
        TogetherVo togetherVo = togetherService.detail(Integer.parseInt(id));
        CourseVo courseVo = courseService.findById(togetherVo.getId());
        UserVo userVo = userService.summary(togetherVo.getUser_id());
        model.addAttribute("together", togetherVo);
        model.addAttribute("course", courseVo.getCoords());
        model.addAttribute("together_user", userVo);
        model.addAttribute("appKey", appKey);
        return new ModelAndView("pages/together/detail");
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<MspResult> get_info(@PathVariable("id") String id) {
        MspResult mspResult;

        TogetherVo togetherVo = new TogetherVo();
        togetherVo.setId(Integer.parseInt(id));
        TogetherVo get_vo = togetherService.info(togetherVo);
        if (get_vo != null) {
            mspResult = MspUtil.makeResult(MspStatus.OK, get_vo);
        } else {
            mspResult = MspUtil.makeResult("404", "존재하지 않는 글입니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<MspResult> search_name(@PathVariable("name") String name) {
        MspResult mspResult;


        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
