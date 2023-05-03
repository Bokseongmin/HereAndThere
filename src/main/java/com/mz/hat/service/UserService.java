package com.mz.hat.service;

import com.mz.hat.dao.UserMapper;
import com.mz.hat.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int signUp(UserVo userVo) {
        int affectRow = userMapper.signUp(userVo);
        logger.debug("SignUp affectRow: {}", affectRow);

        return affectRow;
    }

    public UserVo signIn(UserVo userVo) {
        UserVo vo = userMapper.signIn(userVo);
        if(vo == null) {
            vo = new UserVo();
        }
        System.out.println(vo.toString());
        logger.debug("UserVo: {}", vo);

        return vo;
    }
}
