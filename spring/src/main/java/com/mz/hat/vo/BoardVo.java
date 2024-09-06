package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
public class BoardVo {
    private int id;
    private String user_name;
    private String title;
    private String content;
    private String status;
    private int likes;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;

    private List<ImageVo> images;
}
