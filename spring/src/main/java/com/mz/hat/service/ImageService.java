package com.mz.hat.service;

import com.mz.hat.dao.ImageMapper;
import com.mz.hat.vo.ImageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ImageService {
    @Autowired
    ImageMapper imageMapper;

    public List<ImageVo> get_image(String image_type, int ref_id) {
        return imageMapper.get_image(image_type, ref_id);
    }
}
