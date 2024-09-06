package com.mz.hat.service;

import com.mz.hat.dao.TouristAttrMapper;
import com.mz.hat.vo.TagVo;
import com.mz.hat.vo.TouristAttrVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TouristAttrService {
    @Resource
    private ImageService imageService;
    @Autowired
    private TouristAttrMapper touristAttrMapper;

    public List<TouristAttrVo> findAll() {
        return touristAttrMapper.findAll();
    }

    public List<TouristAttrVo> list(int limit, int offset) {
        List<TouristAttrVo> list = touristAttrMapper.list(limit, offset);
        for(TouristAttrVo vo : list) {
            vo.setImg(imageService.get_image("tourist_attrs", vo.getId()));
        }
        return list;
    }
    public List<TouristAttrVo> tag_list(int tag_id, int limit, int offset) {
        List<TouristAttrVo> list = touristAttrMapper.tag_list(tag_id, limit, offset);
        for(TouristAttrVo vo : list) {
            vo.setImg(imageService.get_image("tourist_attrs", vo.getId()));
        }
        return list;
    }

    public TouristAttrVo search_name(String name) {
        return touristAttrMapper.search_name(name);
    }

    public List<TouristAttrVo> localTour(String local, int limit, int offset) {
        List<TouristAttrVo> list = touristAttrMapper.findByRegion(local, limit, offset);
        for(TouristAttrVo vo : list) {
            vo.setImg(imageService.get_image("tourist_attrs", vo.getId()));
        }
        return list;
    }

    public TouristAttrVo detail(int id) {
        touristAttrMapper.incrementView_cnt(id);
        return touristAttrMapper.detail(id);
    }
}
