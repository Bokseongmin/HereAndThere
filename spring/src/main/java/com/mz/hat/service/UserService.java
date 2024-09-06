package com.mz.hat.service;

import com.mz.hat.dao.UserMapper;
import com.mz.hat.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /*
    이미지 업로드 경로
     */
    @Value("${upload.profile}")
    private String upload_path;

    /*
    회원가입
     */
    public int signUp(UserVo userVo) {
        userVo.setUser_pass(passwordEncoder.encode(userVo.getUser_pass()));
        int affectRow = userMapper.signUp(userVo);
        logger.debug("SignUp affectRow: {}", affectRow);
        return affectRow;
    }

    /*
    이메일 확인
     */
    public int email_check(String user_email) {
        int affectRow = userMapper.email_check(user_email);
        logger.debug("user_info: {}", affectRow);
        return affectRow;
    }

    /*
    로그인
     */
    public UserVo signIn(UserVo userVo) {
        UserVo verifyVo = userMapper.get_user(userVo.getUser_email());
        UserVo signVo = new UserVo();
        if(verifyVo != null && (passwordEncoder.matches(userVo.getUser_pass(), verifyVo.getUser_pass()))) {
            userVo.setUser_pass(verifyVo.getUser_pass());
            signVo = userMapper.signIn(userVo);
        } else {
            signVo = null;
        }
        logger.debug("UserVo: {}", signVo);

        return signVo;
    }

    /*
    유저 정보
     */
    public UserVo info(int id) {
        UserVo userVo = userMapper.info(id);
        if (userVo == null) {
            userVo = new UserVo();
        }
        logger.debug("UserVo: {}", userVo);

        return userVo;
    }

    /*
    유저 요약 정보
     */
    public UserVo summary(int id) {
        return userMapper.summary(id);
    }

    /*
    프로필 사진 변경
     */
    @Transactional
    public int uploadUserProfileImg(MultipartFile file, int user_id) {
        if (!file.isEmpty()) {
            try {
                UUID uuid = UUID.randomUUID();
                String file_name = file.getOriginalFilename();
                String img_upload_path = upload_path + File.separator + uuid + "_" + file_name;
                File uploadDir = new File(upload_path);
                if (!uploadDir.exists()) {
                    boolean dirsCreated = uploadDir.mkdirs();
                    if (!dirsCreated) {
                        throw new IOException("경로 생성 중 오류가 발생했습니다: " + upload_path);
                    }
                }
                file.transferTo(new File(img_upload_path));
                String url = "/user/" + uuid + "_" + file_name;

                return userMapper.insertUserProfileImg(url, user_id);
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        } else {
            return 0;
        }
    }

    /*
    프로필 정보 변경
     */
    @Transactional
    public int updateUserProfile(Map<String, String> data, UserVo sessionVo) {
            UserVo verifyVo = info(sessionVo.getId());

            String user_name = data.get("user_name");
            String currentPassword = data.get("user_pass");
            String newPassword = data.get("new_user_pass");
            String newPasswordCheck = data.get("new_user_pass_check");

            UserVo userVo = new UserVo();

            if (user_name != null && !user_name.isEmpty()) {
                userVo.setUser_name(user_name);
            }

            if (currentPassword != null && newPassword != null && newPasswordCheck != null) {
                if (currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordCheck.isEmpty()) {
                    return 0;
                }
                if (!newPassword.equals(newPasswordCheck)) {
                    return 0;
                }
                if (!passwordEncoder.matches(currentPassword, verifyVo.getUser_pass())) {
                    return 0;
                }
                userVo.setUser_pass(passwordEncoder.encode(newPassword));
            }

            if (userVo.getUser_name() == null && userVo.getUser_pass() == null) {
                return 0;
            }

            userVo.setId(sessionVo.getId());
            return userMapper.updateUserProfile(userVo);
    }
}
