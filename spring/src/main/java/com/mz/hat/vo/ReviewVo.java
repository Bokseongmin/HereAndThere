package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
public class ReviewVo {
    private int id;
    private String title;
    private String tourist_attr_name;
    private int user_id;
    private String user_name;
    private String content;
    private List<ImageVo> img;
    private int likes;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;
}
