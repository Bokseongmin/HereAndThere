package com.mz.hat.controller;

import com.mz.hat.service.CoordService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.CoordVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/coord")
public class CoordController {
    @Resource
    private CoordService coordService;

    @Value("${kakao.secret.map.key}")
    private String appKey;

    /*
    페이지 표시
     */
    @GetMapping
    private ModelAndView home(Model model) {
        model.addAttribute("appKey", appKey);
        return new ModelAndView("/pages/course/write");
    }

    @PostMapping("/add")
    public ResponseEntity<MspResult> get_cord(@RequestBody List<CoordVo> list) {
        MspResult mspResult = null;

//        int affectRow = coordService.insertCoords(list);
//
//        if (affectRow > 0) {
//            mspResult = MspUtil.makeResult(MspStatus.OK, null);
//        } else {
//            mspResult = MspUtil.makeResult("401", "정보를 등록하던 중 오류가 발생했습니다.", null);
//        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }
}
