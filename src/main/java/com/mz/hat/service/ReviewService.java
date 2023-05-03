package com.mz.hat.service;

import com.mz.hat.dao.ReviewMapper;
import com.mz.hat.vo.ReviewVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    public List<ReviewVo> list() {
        List<ReviewVo> reviewVos = reviewMapper.list();
        logger.debug("reviewVos.size: {}", reviewVos.size());
        for(ReviewVo reviewVo: reviewVos) {
            logger.debug(">>>> post: {}", reviewVo);
        }

        return reviewVos;
    }

    public int write(ReviewVo reviewVo) {
        int affectRow = reviewMapper.write(reviewVo);
        logger.debug("INSERT affectRow: {}", affectRow);
        return affectRow;
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
}
