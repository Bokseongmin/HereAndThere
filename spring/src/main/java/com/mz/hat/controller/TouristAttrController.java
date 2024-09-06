package com.mz.hat.controller;

import com.mz.hat.service.ImageService;
import com.mz.hat.service.TouristAttrService;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.ImageVo;
import com.mz.hat.vo.TouristAttrVo;
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
@RequestMapping("/tourist")
@RestController
public class TouristAttrController {

    @Autowired
    private TouristAttrService touristAttrService;

    @Autowired
    private ImageService imageService;

    @Value("${kakao.secret.map.key}")
    private String appKey;

    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable("id") String id, Model model) {
        TouristAttrVo touristAttrVo = touristAttrService.detail(Integer.parseInt(id));
        List<ImageVo> imageVoList = imageService.get_image("tourist_attrs", touristAttrVo.getId());
        model.addAttribute("images", imageVoList);
        model.addAttribute("tourist", touristAttrVo);
        model.addAttribute("appKey", appKey);

        return new ModelAndView("pages/tourist/detail");
    }

    @GetMapping("/list")
    public ResponseEntity<MspResult> list() {
        MspResult result;
        List<TouristAttrVo> list = touristAttrService.findAll();
        if(list != null) {
            result = MspUtil.makeResult(MspStatus.OK, list);
        } else {
            result = MspUtil.makeResult("404", "데이터가 존재 하지 않습니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
