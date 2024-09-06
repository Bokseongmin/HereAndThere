package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class CourseVo {
    private int id;
    private String img;
    private int user_id;
    private String title;
    private String content;
    private int distance;
    private int likes;
    private int views;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

    private List<CoordVo> coords;
    private String user_name;
}
