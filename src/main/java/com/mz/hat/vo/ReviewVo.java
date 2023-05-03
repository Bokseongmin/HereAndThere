package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class ReviewVo {
    private int id;
    private int tourist_attrs_id;
    private int user_id;
    private String content;
    private int likes;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;
}
