package com.mz.hat.service;

import com.mz.hat.dao.CoordMapper;
import com.mz.hat.vo.CoordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CoordService {

    @Autowired
    private CoordMapper coordMapper;

    /*
    course_id를 기준으로 좌표 등록
     */
    @Transactional
    public int insertCoords_course(List<CoordVo> list, int last_insert_id) {
        int affectRow = 0;
        try {
            for (CoordVo vo : list) {
                vo.setEntity_id(last_insert_id);
                int rowsInserted = coordMapper.insertCoords_course(vo);
                if (rowsInserted > 0) {
                    affectRow++;
                } else {
                    throw new RuntimeException("Failed to insert coord: " + vo);
                }
            }
            return affectRow;
        } catch (Exception e) {
            logger.error("Exception occurred during insert: " + e.getMessage());
            throw e;
        }
    }

    /*
    together_id를 기준으로 좌표 등록
     */
    @Transactional
    public int insertCoords_together(List<CoordVo> list, int last_insert_id) {
        int affectRow = 0;
        try {
            for (CoordVo vo : list) {
                vo.setEntity_id(last_insert_id);
                int rowsInserted = coordMapper.insertCoords_together(vo);
                if (rowsInserted > 0) {
                    affectRow++;
                } else {
                    throw new RuntimeException("Failed to insert coord: " + vo);
                }
            }
            return affectRow;
        } catch (Exception e) {
            logger.error("Exception occurred during insert: " + e.getMessage());
            throw e;
        }
    }

    /*
    Course_id 기준으로 좌표 조회
     */
    public List<CoordVo> findByCourse_id(int course_id) {
        try {
            return coordMapper.findByCourse_id(course_id);
        } catch (Exception e) {
            logger.error("Exception occurred during select: " + e.getMessage());
            throw e;
        }
    }

    /*
    Course_id 기준으로 좌표 조회
     */
    public List<CoordVo> findByTogether_id(int id) {
        try {
            return coordMapper.findByTogether_id(id);
        } catch (Exception e) {
            logger.error("Exception occurred during select: " + e.getMessage());
            throw e;
        }
    }
}
