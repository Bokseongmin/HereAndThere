package com.mz.hat.controller;

import com.mz.hat.service.ImageService;
import com.mz.hat.service.TouristAttrService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.TouristAttrVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Resource
    private TouristAttrService touristAttrService;

    @Resource
    private ImageService imageService;

    @GetMapping
    public ModelAndView region() {
        return new ModelAndView("/pages/region/list");
    }

    @GetMapping("/{region}")
    public ResponseEntity<MspResult> list(@PathVariable String region,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "0") int page) {
        MspResult mspResult;
        List<TouristAttrVo> list = new ArrayList<>();
        if(region.equals("korea")) {
            list = touristAttrService.list(size, page);
        } else {
            list = touristAttrService.localTour(region, size, page);
        }

        if(!list.isEmpty()) {
            mspResult = MspUtil.makeResult(MspStatus.OK, list);
        } else {
            mspResult = MspUtil.makeResult("404", "데이터가 존재하지 않습니다.", null);
        }
        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }
}
