package com.mz.hat.service;

import com.mz.hat.dao.TagMapper;
import com.mz.hat.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    public int tag_verify(TagVo tagVo) {
        return tagMapper.verify(tagVo);
    }

    public List<TagVo> get_tourist_tags(int tour_id) {
        return tagMapper.findByTour_id(tour_id);
    }
}
