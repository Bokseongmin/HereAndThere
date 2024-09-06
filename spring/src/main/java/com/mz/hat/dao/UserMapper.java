package com.mz.hat.dao;

import com.mz.hat.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    // 회원가입
    Integer signUp(UserVo userVo);

    // 이메일 확인
    Integer email_check(String user_email);

    // 로그인 정보
    UserVo signIn(UserVo userVo);

    // 유저 정보 조회 (user_email)
    UserVo get_user(String user_email);

    // 유저 정보 (id)
    UserVo info(int id);

    // 유저 정보 요약 (id)
    UserVo summary(int id);

    // 유저 프로필 등록 및 변경
    Integer insertUserProfileImg(@Param("user_img") String url,@Param("id") int id);

    // 유저 프로필 수정
    Integer updateUserProfile(UserVo userVo);
}
