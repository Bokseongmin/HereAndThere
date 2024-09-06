package com.mz.hat.controller;

import com.mz.hat.service.BoardService;
import com.mz.hat.service.ImageService;
import com.mz.hat.service.ReviewService;
import com.mz.hat.service.TouristAttrService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.ReviewVo;
import com.mz.hat.vo.TouristAttrVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@MSP
@Slf4j
@RestController
public class HomeController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private TouristAttrService touristAttrService;

    @Value("${kakao.secret.map.key}")
    private String appKey;

    @GetMapping("/")
    public ModelAndView home(Model model) {
        model.addAttribute("appKey", appKey);
        return new ModelAndView("index");
    }

    @GetMapping("/get/reviews")
    public ResponseEntity<MspResult> get_reviews() {
        MspResult mspResult;

        List<ReviewVo> reviewVoList = reviewService.top10List();

        int affectRow = reviewVoList.size();

        for(ReviewVo reviewVo: reviewVoList) {
            reviewVo.setImg(imageService.get_image("reviews", reviewVo.getId()));
        }

        if(affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, reviewVoList);
        } else {
            mspResult = MspUtil.makeResult("404", "결과를 찾을 수 없습니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/get/posts")
    public ResponseEntity<MspResult> get_posts() {

        return null;
    }

    @GetMapping("/error")
    public ResponseEntity<MspResult> error() {
        MspResult mspResult = MspUtil.makeResult("400", "잘못된 요청입니다.", null);
        return new ResponseEntity<>(mspResult, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/search")
    public ResponseEntity<MspResult> search(@RequestParam("keyword") String keyword) {
        MspResult mspResult;

        TouristAttrVo touristAttrVo = touristAttrService.search_name(keyword);
        touristAttrVo.setImg(imageService.get_image("tourist_attrs", touristAttrVo.getId()));

        if(touristAttrVo != null) {
            mspResult = MspUtil.makeResult(MspStatus.OK, touristAttrVo);
        } else {
            mspResult = MspUtil.makeResult("404", "결과를 찾을 수 없습니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }
}
