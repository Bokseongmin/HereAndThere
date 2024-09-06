package com.mz.hat.controller;

import com.mz.hat.service.BoardService;
import com.mz.hat.service.ImageService;
import com.mz.hat.service.RegionService;
import com.mz.hat.support.msp.MspUtil;
import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.msp.result.MspResult;
import com.mz.hat.support.msp.result.MspStatus;
import com.mz.hat.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/board")
@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private ImageService imageService;

    @GetMapping()
    public ModelAndView home() {
        return new ModelAndView("pages/board/list");
    }

    @GetMapping("/list/get")
    public ResponseEntity<MspResult> get_list(@RequestParam("page") int page) {
        MspResult mspResult;

        int page_size = 2;
        int offset = (page - 1) * page_size;

        List<BoardVo> boardVos = boardService.list(offset, page_size);

        int boardVosSize = boardVos.size();

        if (boardVosSize > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, boardVos);
        } else {
            mspResult = MspUtil.makeResult("8888", "등록된 게시글이 없습니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/write")
    public ModelAndView get_write(Model model) {
        List<RegionVo> regionVos = regionService.list();
        model.addAttribute("regions", regionVos);
        return new ModelAndView("pages/board/write");
    }

    @PostMapping("/write")
    public ResponseEntity<MspResult> board_write(@RequestPart(value = "board") Map<String, String> map,
                                                 @RequestPart(value = "file_name", required = false) List<MultipartFile> images,
                                                 HttpSession session) throws IOException {
        MspResult mspResult;

        BoardVo boardVo = new BoardVo();
        UserVo userVo = (UserVo) session.getAttribute("user");
        boardVo.setUser_name(userVo.getUser_name());
        boardVo.setTitle(map.get("title"));
        boardVo.setContent(map.get("content"));

        int affectRow = boardService.write(boardVo, images);

        if (affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, boardVo);
        } else {
            mspResult = MspUtil.makeResult("400", "중복된 글입니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable("id") String id, Model model) {
        BoardVo boardVo = boardService.detail(Integer.parseInt(id));
        List<ImageVo> imageVoList = imageService.get_image("board", boardVo.getId());

        System.out.println(imageVoList.toString());
        model.addAttribute("images", imageVoList);
        model.addAttribute("board", boardVo);
        return new ModelAndView("pages/board/detail");
    }

    @PostMapping("/{id}")
    public ResponseEntity<MspResult> board_modify(@PathVariable("id") String id, @RequestBody BoardVo boardVo) {
        MspResult mspResult;

        boardVo.setId(Integer.parseInt(id));
        int affectRow = boardService.modify(boardVo);
        if (affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, boardVo);
        } else {
            mspResult = MspUtil.makeResult("404", "존재하지 않는 글입니다.", boardVo);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MspResult> board_delete(@PathVariable("id") String id) {
        MspResult mspResult;

        BoardVo vo = new BoardVo();
        vo.setId(Integer.parseInt(id));
        int affectRow = boardService.delete(vo);

        if (affectRow > 0) {
            mspResult = MspUtil.makeResult(MspStatus.OK, vo);
        } else {
            mspResult = MspUtil.makeResult("404", "존재하지 않는 글입니다.", null);
        }

        return new ResponseEntity<>(mspResult, HttpStatus.OK);
    }
}
