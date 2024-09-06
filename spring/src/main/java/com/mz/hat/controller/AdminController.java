package com.mz.hat.controller;

import com.mz.hat.service.AdminService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.TouristAttrVo;
import com.mz.hat.vo.UserVo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MSP
@RequestMapping("/admin")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Value("${google.secret.map.key}")
    private String apiKey;

    @GetMapping("/dashboard")
    public ModelAndView get_admin() {
        return new ModelAndView("admin/dashboard");
    }

    @GetMapping("/user_list")
    public ModelAndView user_list() {
        return new ModelAndView("admin/user_list");
    }

    @GetMapping("/user_list/get")
    public ResponseEntity<MspResult> get_user_list() {
        MspResult result;

        List<UserVo> userVos = adminService.get_user_list();

        int affectRow = userVos.size();

        if (affectRow > 0) {
            result = MspUtil.makeResult(MspStatus.OK, userVos);
        } else {
            result = MspUtil.makeResult("404", "등록된 데이터가 없습니다.", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/tour_list")
    public ModelAndView tour_list() {
        return new ModelAndView("admin/tour_list");
    }

    @GetMapping("/tour_list/get")
    public ResponseEntity<MspResult> get_tour_list(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        MspResult result;

        List<TouristAttrVo> tourist_list = adminService.get_tour_list(page, size);

        int affectRow = tourist_list.size();
        int tourist_cnt = adminService.tourist_cnt();
        int tourist_page = (int) Math.ceil((double) tourist_cnt / page);

        int start_page = Math.max(1, page - 5);
        int end_page = Math.min(tourist_page, page + 5);

        if (affectRow > 0) {
            Map<String, Object> res = new HashMap<>();
            res.put("tourist", tourist_list);
            res.put("total", tourist_cnt);
            res.put("current", page);
            res.put("page", tourist_page);
            res.put("start", start_page);
            res.put("end", end_page);
            result = MspUtil.makeResult(MspStatus.OK, res);
        } else {
            result = MspUtil.makeResult("400", "데이터 오류", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/tour_list/{id}")
    public ModelAndView detail(@PathVariable("id") String id, Model model) {
        TouristAttrVo touristAttrVo = adminService.get_tour_detail(Integer.parseInt(id));
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("tour", touristAttrVo);
        return new ModelAndView("admin/tour_detail");
    }

    @PostMapping("/tour_list/{id}")
    public ResponseEntity<MspResult> detail(@RequestPart(value = "tour_detail") Map<String, String> map,
                                            @RequestPart(value = "file_name", required = false) List<MultipartFile> images,
                                            HttpSession session) throws IOException {
        MspResult mspResult;

        TouristAttrVo touristAttrVo = new TouristAttrVo();
        touristAttrVo.setId(Integer.parseInt(map.get("id")));
        touristAttrVo.setContent(map.get("content"));
        touristAttrVo.setName(map.get("name"));
        touristAttrVo.setCategory_name(map.get("category_name"));
        touristAttrVo.setRoad_address(map.get("road_address"));

        int affectRow = adminService.tour_modify(touristAttrVo, images);

        if (affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, touristAttrVo);
        } else {
            mspResult = MspUtil.makeResult("400", "데이터 오류", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @PostMapping("/tour_list/add")
    public ResponseEntity<MspResult> add_tour_list() {
        MspResult result;
        int affectRow = adminService.add_tour_list();

        if (affectRow > 0) {
            result = MspUtil.makeResult(MspStatus.OK, null);
        } else {
            result = MspUtil.makeResult("400", "데이터 오류", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/category/get_cnt")
    public ResponseEntity<MspResult> get_cnt() {
        MspResult mspResult;

        Map<String, Integer> data = adminService.get_cnt();

        if (data.size() > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, data);
        } else {
            mspResult = MspUtil.makeResult("404", "데이터가 없습니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }
}
