package com.mz.hat.controller;

import com.mz.hat.service.CategoryService;
import com.mz.hat.service.ImageService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.CategoryVo;
import com.mz.hat.vo.TouristAttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@MSP
@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

//    @GetMapping("/get")
//    public ResponseEntity<MspResult> list() {
//        MspResult mspResult;
//        List<CategoryVo> categoryVos = categoryService.list();
//
//        int categoryVosSize = categoryVos.size();
//
//        if(categoryVosSize > 0) {
//            mspResult = MspUtil.makeResult(MspStatus.OK, categoryVos);
//        } else {
//            mspResult = MspUtil.makeResult("8888", "등록된 카테고리가 없습니다.", null);
//        }
//        return new ResponseEntity<>(mspResult, HttpStatus.OK);
//    }

    @GetMapping("/{category_name}")
    public ResponseEntity<MspResult> get_page(@PathVariable String category_name, Model model) {
        MspResult mspResult;
        List<TouristAttrVo> category_pages = categoryService.category_page(category_name);
        for(TouristAttrVo touristAttrVo: category_pages) {
            touristAttrVo.setImg(imageService.get_image("tourist_attrs", touristAttrVo.getId()));
        }
        int category_page_size = category_pages.size();

        if(category_page_size > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, category_pages);
        } else {
            mspResult = MspUtil.makeResult("404", "등록된 데이터가 없습니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }
}
