package com.mz.hat.service;

import com.mz.hat.dao.ImageMapper;
import com.mz.hat.dao.ReviewMapper;
import com.mz.hat.vo.ImageType;
import com.mz.hat.vo.ImageVo;
import com.mz.hat.vo.ReviewVo;
import com.mz.hat.vo.TogetherVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ResourceLoader resourceLoader;

    public List<ReviewVo> list() {
        List<ReviewVo> reviewVos = reviewMapper.list();
        logger.debug("reviewVos.size: {}", reviewVos.size());
        for(ReviewVo reviewVo: reviewVos) {
            logger.debug(">>>> post: {}", reviewVo);
        }

        return reviewVos;
    }

    public int write(ReviewVo reviewVo, List<MultipartFile> images) throws IOException {
        int affectRow = reviewMapper.write(reviewVo);
        int row_id = reviewVo.getId();

        if(affectRow > 0 && images != null && !images.isEmpty()) {
            for(MultipartFile image : images) {
                String image_name = image.getOriginalFilename();
                String image_path = System.currentTimeMillis() + "_" + image_name;

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
                imagesVo.setImage_type(ImageType.reviews);
                imagesVo.setRef_id(row_id);
                imageMapper.insert_image(imagesVo);
            }
        }

        logger.debug("INSERT affectRow: {}", affectRow);
        return affectRow;
    }

    public ReviewVo detail(int id) {
        ReviewVo reviewVo = reviewMapper.detail(id);
        if (reviewVo == null) {
            reviewVo = new ReviewVo();
        }
        logger.debug("get_vo: {}", reviewVo);

        return reviewVo;
    }

    public int modify(ReviewVo reviewVo) {
        int affectRow = reviewMapper.modify(reviewVo);
        logger.debug("UPDATE affectRow: {}", affectRow);
        return affectRow;
    }

    public int delete(ReviewVo reviewVo) {
        int affectRow = reviewMapper.delete(reviewVo);
        logger.debug("DELETE affectRow: {}", affectRow);
        return affectRow;
    }

    public List<ReviewVo> top10List() {
        List<ReviewVo> reviewVos = reviewMapper.top10();
        logger.debug("reviewVos.size: {}", reviewVos.size());
        for(ReviewVo reviewVo: reviewVos) {
            logger.debug(">>>> post: {}", reviewVo);
        }

        return reviewVos;
    }
}
