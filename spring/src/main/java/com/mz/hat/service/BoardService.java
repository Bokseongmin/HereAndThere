package com.mz.hat.service;

import com.mz.hat.dao.BoardMapper;
import com.mz.hat.dao.ImageMapper;
import com.mz.hat.vo.BoardVo;
import com.mz.hat.vo.ImageType;
import com.mz.hat.vo.ImageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class BoardService {
    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ResourceLoader resourceLoader;

    public List<BoardVo> list(int offset, int page_size) {
        List<BoardVo> boardVos = boardMapper.list(offset, page_size);
        logger.debug("boardVos.size: {}", boardVos.size());
        for(BoardVo boardVo : boardVos) {
            List<ImageVo> imageVos = imageMapper.get_image("board", boardVo.getId());
            boardVo.setImages(imageVos);
            logger.debug(">>>> board: {}", boardVo);
        }
        return boardVos;
    }

    public int write(BoardVo boardVo, List<MultipartFile> images) throws IOException {
        int affectRow = boardMapper.write(boardVo);
        int row_id = boardVo.getId();

        if(affectRow > 0 && images != null && !images.isEmpty()) {
            for(MultipartFile image : images) {
                String image_name = image.getOriginalFilename();
                String image_path = System.currentTimeMillis() + "_" + image_name;

                Resource resource = resourceLoader.getResource("classpath:static");
                String absolute_path = resource.getFile().getAbsolutePath();
                File directory = new File(absolute_path + "\\images");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String save_path = directory.getAbsolutePath() + "\\" + image_path;

                image.transferTo(new File(save_path));

                ImageVo imagesVo = new ImageVo();
                imagesVo.setImage_name(image_name);
                imagesVo.setImage_path(image_path);
                imagesVo.setImage_type(ImageType.board);
                imagesVo.setRef_id(row_id);
                imageMapper.insert_image(imagesVo);
            }
        }
        return affectRow;
    }

    public BoardVo detail(int id) {
        BoardVo boardVo = boardMapper.detail(id);
        return boardVo;
    }

    public int modify(BoardVo boardVo) {
        int affectRow = boardMapper.modify(boardVo);
        logger.debug("UPDATE affectRow: {}", affectRow);
        return affectRow;
    }

    public int delete(BoardVo boardVo) {
        int affectRow = boardMapper.delete(boardVo);
        logger.debug("DELETE affectRow: {}", affectRow);
        return affectRow;
    }
}
