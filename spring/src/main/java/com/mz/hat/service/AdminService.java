package com.mz.hat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mz.hat.dao.AdminMapper;
import com.mz.hat.dao.ImageMapper;
import com.mz.hat.support.utill.JsonLoader;
import com.mz.hat.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ResourceLoader resourceLoader;

    public List<UserVo> get_user_list() {
        List<UserVo> userVos = adminMapper.get_user_list();
        logger.debug("userVos.size: {}", userVos.size());
        for(UserVo userVo: userVos) {
            logger.debug(">>>> userVo: {}", userVo);
        }

        return userVos;
    }

//    public List<UserVo> list(int offset, int page_size) {
//        List<UserVo> userVos = adminMapper.load_user_list();
//        logger.debug("userVos.size: {}", userVos.size());
//        for(UserVo userVo : userVos) {
//            logger.debug(">>>> userVo: {}", userVo);
//        }
//        return userVos;
//    }

    public List<TouristAttrVo> get_tour_list(int page, int size) {
        int offset = (page - 1 ) * size;
        return adminMapper.get_tour_list(offset, size);
    }

    public int tourist_cnt() {
        return adminMapper.cntTourist();
    }

    public TouristAttrVo get_tour_detail(int id) {
        TouristAttrVo touristAttrVo = adminMapper.get_tour_detail(id);
        return touristAttrVo;
    }

    public int tour_modify(TouristAttrVo touristAttrVo, List<MultipartFile> images) throws IOException {
        int affectRow = adminMapper.tour_modify(touristAttrVo);
        int row_id = touristAttrVo.getId();

        if(affectRow > 0 && images != null && !images.isEmpty()) {
            for(MultipartFile image : images) {
                String image_name = image.getOriginalFilename();
                String image_path = row_id + "\\" + System.currentTimeMillis() + "_" + image_name;

                Resource resource = resourceLoader.getResource("classpath:static");
                String absolute_path = resource.getFile().getAbsolutePath();
                File directory = new File(absolute_path + "\\images");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String save_path = directory.getAbsolutePath() + "\\" + image_path;

                image.transferTo(new File(save_path));

                ImageVo imagesVo = new ImageVo();
                imagesVo.setImage_name(image_name);
                imagesVo.setImage_path(image_path);
                imagesVo.setImage_type(ImageType.tourist_attrs);
                imagesVo.setRef_id(row_id);
                imageMapper.insert_image(imagesVo);
            }
        }
        return affectRow;
    }

    public int add_tour_list() {
        try {
            JsonLoader jsonLoader = new JsonLoader(objectMapper);
            List<TouristAttrVo> touristAttrVoList = jsonLoader.load_tourist_attr();

            for(TouristAttrVo touristAttrVo: touristAttrVoList) {
                TouristAttrVo new_tourist = new TouristAttrVo();

                new_tourist.setName(touristAttrVo.getName());
                new_tourist.setContent(touristAttrVo.getContent());
                new_tourist.setRoad_address(touristAttrVo.getRoad_address());
                new_tourist.setParcel_address(touristAttrVo.getParcel_address());
                new_tourist.setLat(touristAttrVo.getLat());
                new_tourist.setLng(touristAttrVo.getLng());
                new_tourist.setTel(touristAttrVo.getTel());
                new_tourist.setAssigned_date(touristAttrVo.getAssigned_date());

                adminMapper.add_tour_list(new_tourist);
            }
            return touristAttrVoList.size();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Map<String, Integer> get_cnt() {
        Map<String, Integer> category_name_cnt = new HashMap<>();

        String[] category_names = {"가족", "친구", "연인", "혼자", "문화", "자연", "휴양"};

        for(String category_name: category_names) {
            int cnt = adminMapper.get_cnt(category_name);
            category_name_cnt.put(category_name, cnt);
        }

        category_name_cnt.put("전체", adminMapper.all_cnt());

        return category_name_cnt;
    }
}
