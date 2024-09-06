package com.mz.hat.service;

import com.mz.hat.dao.UserTagMapper;
import com.mz.hat.vo.UserTagVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserTagService {
    @Autowired
    private UserTagMapper userTagMapper;

    public List<UserTagVo> info(UserTagVo userTagVo){
        return userTagMapper.info(userTagVo);
    }

    public List<UserTagVo> get(int tour_id) {
        return userTagMapper.get(tour_id);
    }

    public int add(UserTagVo userTagVo) {
        try {
            return userTagMapper.add(userTagVo);
        } catch (DuplicateKeyException e) {
            logger.error("중복된 값이 존재합니다. - " + e.getMessage());
            return 0;
        } catch (Exception e) {
            logger.error("오류가 발생했습니다. - " + e.getMessage());
            return 0;
        }
    }

    public int delete(UserTagVo userTagVo) {
        try {
            return userTagMapper.delete(userTagVo);
        } catch (Exception e) {
            logger.error("오류가 발생했습니다. - " + e.getMessage());
            return 0;
        }
    }
}
