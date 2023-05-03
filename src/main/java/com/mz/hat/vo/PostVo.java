package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class PostVo {
    private int id;
    private int user_id;
    private String title;
    private String content;
    private String status;
    private int likes;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;
}
