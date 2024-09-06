package com.mz.hat.controller;

import com.mz.hat.service.ImageService;
import com.mz.hat.service.ReviewService;
import com.mz.hat.service.TouristAttrService;
import com.mz.hat.service.UserService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@MSP
@Slf4j
@RequestMapping("/review")
@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private TouristAttrService touristAttrService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Value("${google.secret.map.key}")
    private String apiKey;

    @GetMapping()
    public ModelAndView home() {
        return new ModelAndView("pages/review/list");
    }

    @GetMapping("/list/get")
    public ResponseEntity<MspResult> get_list() {
        MspResult mspResult;
        List<ReviewVo> reviewVos = reviewService.list();
        for(ReviewVo reviewVo: reviewVos) {
            List<ImageVo> imageVoList = imageService.get_image("reviews", reviewVo.getId());
            reviewVo.setImg(imageVoList);
        }
        int reviewVoSize = reviewVos.size();

        if(reviewVoSize > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, reviewVos);
        } else {
            mspResult = MspUtil.makeResult("8888", "등록된 게시글이 없습니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/write")
    public ModelAndView get_write() {
        return new ModelAndView("pages/review/write");
    }

    @PostMapping("/write")
    public ResponseEntity<MspResult> post_write(@RequestPart(value = "review") Map<String, String> map,
                                                @RequestPart(value = "file_name", required = false) List<MultipartFile> images,
                                                HttpSession session) throws IOException {
        MspResult mspResult;

        ReviewVo reviewVo = new ReviewVo();
        UserVo userVo = (UserVo) session.getAttribute("user");
        reviewVo.setTitle(map.get("title"));
        reviewVo.setTourist_attr_name(map.get("tourist_attr_name"));
        reviewVo.setUser_name(userVo.getUser_name());
        reviewVo.setContent(map.get("content"));

        System.out.println(reviewVo);
        int affectRow = reviewService.write(reviewVo, images);

        if(affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, reviewVo);
        } else {
            mspResult = MspUtil.makeResult("400", "중복된 글입니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable("id") String id, Model model) {
        ReviewVo reviewVo = reviewService.detail(Integer.parseInt(id));
        List<ImageVo> imageVoList = imageService.get_image("reviews", reviewVo.getId());

        model.addAttribute("images", imageVoList);
        model.addAttribute("review", reviewVo);
        model.addAttribute("apiKey", apiKey);
        return new ModelAndView("pages/review/detail");
    }

    @PostMapping("/{id}")
    public ResponseEntity<MspResult> post_modify(@PathVariable("id") String id ,@RequestBody ReviewVo reviewVo) {
        MspResult mspResult;

        reviewVo.setId(Integer.parseInt(id));
        int affectRow = reviewService.modify(reviewVo);
        if(affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, reviewVo);
        } else {
            mspResult = MspUtil.makeResult("404", "존재하지 않는 글입니다.", reviewVo);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MspResult> del_delete(@PathVariable("id") String id) {
        MspResult mspResult;

        ReviewVo vo = new ReviewVo();
        vo.setId(Integer.parseInt(id));
        int affectRow = reviewService.delete(vo);

        if(affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, vo);
        } else {
            mspResult = MspUtil.makeResult("404", "존재하지 않는 글입니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }
}
