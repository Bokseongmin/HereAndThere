package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class UserVo {
    private int id;
    private String user_name;
    private String user_email;
    private String user_pass;
    private String user_region;
    private String user_img;
    private String role;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;
}
