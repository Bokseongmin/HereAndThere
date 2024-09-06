package com.mz.hat.service;

import com.mz.hat.dao.RegionMapper;
import com.mz.hat.vo.RegionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RegionService {
    @Autowired
    private RegionMapper regionMapper;

    public List<RegionVo> list() {
        List<RegionVo> regionVos = regionMapper.list();
        logger.debug("regionVos.size: {}", regionVos.size());
        for(RegionVo regionVo: regionVos) {
            logger.debug(">>>> region: {}", regionVo);
        }
        return regionVos;
    }
}
