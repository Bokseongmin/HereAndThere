package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class ImageVo {
    private int id;
    private String image_name;
    private String image_path;
    private ImageType image_type;
    private LocalDateTime reg_date;
    private int ref_id;
}
