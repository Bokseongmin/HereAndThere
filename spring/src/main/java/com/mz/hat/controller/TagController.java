package com.mz.hat.controller;

import com.mz.hat.service.ImageService;
import com.mz.hat.service.TagService;
import com.mz.hat.service.TouristAttrService;
import com.mz.hat.service.UserTagService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.TagVo;
import com.mz.hat.vo.TouristAttrVo;
import com.mz.hat.vo.UserTagVo;
import com.mz.hat.vo.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;
    @Resource
    private UserTagService userTagService;
    @Resource
    private TouristAttrService touristAttrService;
    @Resource
    private ImageService imageService;

    @GetMapping("/list/{tag_id}")
    public ResponseEntity<MspResult> list(@PathVariable int tag_id,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "0") int page) {
        MspResult mspResult;
        List<TouristAttrVo> tag_pages = touristAttrService.tag_list(tag_id, size, page);
        for (TouristAttrVo touristAttrVo : tag_pages) {
            touristAttrVo.setImg(imageService.get_image("tourist_attrs", touristAttrVo.getId()));
        }
        int category_page_size = tag_pages.size();

        if (category_page_size > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, tag_pages);
        } else {
            mspResult = MspUtil.makeResult("404", "등록된 데이터가 없습니다.", null);
        }
        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/get/{tour_id}")
    public ResponseEntity<MspResult> info(@PathVariable int tour_id) {
        MspResult mspResult;
        List<UserTagVo> list = userTagService.get(tour_id);
        if (list != null) {
            mspResult = MspUtil.makeResult(MspStatus.OK, list);
        } else {
            mspResult = MspUtil.makeResult("404", "등록된 데이터가 없습니다.", null);
        }
        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/info/{tour_id}")
    public ResponseEntity<MspResult> info(@PathVariable int tour_id, HttpSession httpSession, HttpServletRequest request) {
        MspResult mspResult;
        UserVo userVo = (UserVo) httpSession.getAttribute("user");
        String ipAddress = request.getRemoteAddr();
        UserTagVo userTagVo = new UserTagVo();
        if (userVo != null) {
            userTagVo.setUser_id(userVo.getId());
        } else {
            userTagVo.setIp_address(ipAddress);
        }
        userTagVo.setTour_id(tour_id);

        List<UserTagVo> list = userTagService.info(userTagVo);
        if (list != null) {
            mspResult = MspUtil.makeResult(MspStatus.OK, list);
        } else {
            mspResult = MspUtil.makeResult("400", "태그 정보를 불러오던 중 오류가 발생했습니다.", null);
        }
        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @PostMapping("/add/{tour_id}")
    public ResponseEntity<MspResult> add(@PathVariable int tour_id, @RequestParam String tag, @RequestParam int tag_id, HttpSession httpSession, HttpServletRequest request) {
        MspResult mspResult;

        UserVo userVo = (UserVo) httpSession.getAttribute("user");
        String ipAddress = request.getRemoteAddr();

        UserTagVo userTagVo = new UserTagVo();
        if (userVo != null) {
            userTagVo.setUser_id(userVo.getId());
        } else {
            userTagVo.setIp_address(ipAddress);
        }
        userTagVo.setTour_id(tour_id);

        TagVo tagVo = new TagVo();
        tagVo.setTag_eng(tag);
        tagVo.setId(tag_id);
        int tag_verify = tagService.tag_verify(tagVo);
        if (tag_verify <= 0) {
            mspResult = MspUtil.makeResult("404", "올바르지 않는 태그 입니다.", null);
            return new ResponseEntity<>(mspResult, HttpStatus.OK);
        }
        userTagVo.setTag_id(tag_id);
        int affectRow = userTagService.add(userTagVo);
        if (affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, null);
        } else {
            int affectRow2 = userTagService.delete(userTagVo);
            if (affectRow2 > 0) {
                mspResult = MspUtil.makeResult(MspStatus.OK, null);
            } else {
                mspResult = MspUtil.makeResult("400", "태그 등록 중 오류가 발생했습니다.", null);
            }
        }
        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @PostMapping("/delete/{tour_id}")
    public ResponseEntity<MspResult> delete(@PathVariable int tour_id, @RequestParam String tag, @RequestParam int tag_id, HttpSession httpSession, HttpServletRequest request) {
        MspResult mspResult;
        UserVo userVo = (UserVo) httpSession.getAttribute("user");
        String ipAddress = request.getRemoteAddr();
        UserTagVo userTagVo = new UserTagVo();
        userTagVo.setTag_id(tag_id);
        userTagVo.setTour_id(tour_id);
        if (userVo != null) {
            userTagVo.setUser_id(userVo.getId());
        } else {
            userTagVo.setIp_address(ipAddress);
        }
        TagVo tagVo = new TagVo();
        tagVo.setTag_eng(tag);
        tagVo.setId(tag_id);
        int tag_verify = tagService.tag_verify(tagVo);
        if (tag_verify <= 0) {
            mspResult = MspUtil.makeResult("404", "올바르지 않는 태그 입니다.", null);
            return new ResponseEntity<>(mspResult, HttpStatus.OK);
        }
        int affectRow = userTagService.delete(userTagVo);
        if (affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, null);
        } else {
            mspResult = MspUtil.makeResult("400", "태그 삭제 중 오류가 발생했습니다.", null);
        }
        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }
}
