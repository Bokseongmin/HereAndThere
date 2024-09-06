package com.mz.hat.service;

import com.mz.hat.dao.CourseMapper;
import com.mz.hat.vo.CoordVo;
import com.mz.hat.vo.CourseVo;
import com.mz.hat.vo.RouteVo;
import com.mz.hat.vo.TouristAttrVo;
import jdk.nashorn.internal.ir.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Resource
    private CoordService coordService;
    @Resource
    private UserService userService;

    @Value("${upload.course}")
    private String upload_path;
    /*
    코스 등록
     */
    @Transactional
    public int insertCourse(CourseVo courseVo) {
        try {
            int affectRow = courseMapper.insertCourse(courseVo);
            if (affectRow > 0) {
                return coordService.insertCoords_course(courseVo.getCoords(), courseVo.getId());
            } else {
                return 0;
            }
        } catch (Exception e) {
            logger.error("Exception occurred during insert: " + e.getMessage());
            throw e;
        }
    }

    /*
    코스 조회
     */
    public List<CourseVo> findAll(int page, int size) {
        int offset = (page - 1 ) * size;
        List<CourseVo> list = courseMapper.findAll(offset, size);
        for(CourseVo courseVo: list) {
            courseVo.setUser_name(userService.summary(courseVo.getUser_id()).getUser_name());
        }
        return list;
    }

    /*
    코스 전체 갯수
     */
    public int course_cnt() {
        return courseMapper.course_cnt();
    }

    /*
    코스 상세조회
     */
    @Transactional(readOnly = true)
    public CourseVo findById(int id) {
        try {
            CourseVo course = courseMapper.findById(id);
            List<CoordVo> list = coordService.findByCourse_id(id);
            List<CoordVo> coords = new ArrayList<>();
            for(CoordVo coordVo: list) {
                CoordVo coordVo1 = new CoordVo();
                coordVo1.setLat(coordVo.getLat());
                coordVo1.setLng(coordVo.getLng());
                coords.add(coordVo1);
            }
            course.setCoords(coords);
            return course;
        } catch (Exception e) {
            logger.error("Exception occurred during select: " + e.getMessage());
            throw e;
        }
    }

    /*
    코스 수정
     */
    public int updateById(int id, CourseVo courseVo) {
        return courseMapper.updateById(courseVo);
    }
}
