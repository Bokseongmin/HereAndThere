package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
@ToString
@Getter
@Setter
public class TogetherVo {
    private int id;
    private int user_id;
    private int region_id;
    private int together_id;
    private String title;
    private String content;
    private int guests;
    private List<UserVo> guest_list;
    private List<ImageVo> img;
    private String routes;
    private LocalDateTime departure_date;
    private LocalDateTime arrival_date;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;

    private String user_name;
}
