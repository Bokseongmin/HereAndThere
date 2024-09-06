package com.mz.hat.service;

import com.mz.hat.dao.CategoryMapper;
import com.mz.hat.vo.CategoryVo;
import com.mz.hat.vo.RegionVo;
import com.mz.hat.vo.TouristAttrVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryVo> list() {
        List<CategoryVo> categoryVos = categoryMapper.list();
        logger.debug("categoryVos.size: {}", categoryVos.size());
        for(CategoryVo categoryVo: categoryVos) {
            logger.debug(">>>> categoryVo: {}", categoryVo);
        }

        return categoryVos;
    }

    public List<TouristAttrVo> category_page(String category_name) {
        List<TouristAttrVo> touristAttrVoList = categoryMapper.category_page(converter(category_name));
        logger.debug("touristAttrVoList.size: {}", touristAttrVoList.size());
        for(TouristAttrVo touristAttrVo: touristAttrVoList) {
            logger.debug(">>>> touristAttrVo: {}", touristAttrVo);
        }

        return touristAttrVoList;
    }

    public String converter(String english) {
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap.put("family", "가족");
        categoryMap.put("friends", "친구");
        categoryMap.put("couple", "연인");
        categoryMap.put("solo", "혼자");
        categoryMap.put("culture", "문화");
        categoryMap.put("nature", "자연");
        categoryMap.put("vacation", "휴양");

        return categoryMap.get(english);
    }
}
