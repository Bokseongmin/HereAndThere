package com.mz.hat.service;

import com.mz.hat.dao.PostMapper;
import com.mz.hat.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    public List<PostVo> list() {
        List<PostVo> postVos = postMapper.list();
        logger.debug("postVos.size: {}", postVos.size());
        for(PostVo postVo: postVos) {
            logger.debug(">>>> post: {}", postVo);
        }

        return postVos;
    }

    public int write(PostVo postVo) {
        int affectRow = postMapper.write(postVo);
        logger.debug("INSERT affectRow: {}", affectRow);
        return affectRow;
    }

    public int modify(PostVo postVo) {
        int affectRow = postMapper.modify(postVo);
        logger.debug("UPDATE affectRow: {}", affectRow);
        return affectRow;
    }

    public int delete(PostVo postVo) {
        int affectRow = postMapper.delete(postVo);
        logger.debug("DELETE affectRow: {}", affectRow);
        return affectRow;
    }
}
