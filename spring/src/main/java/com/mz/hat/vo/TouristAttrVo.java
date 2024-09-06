package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
public class TouristAttrVo {
    private int id;
    private String category_name;
    private int region_id;
    private String name;
    private String content;
    private String road_address;
    private String parcel_address;
    private String lat;
    private String lng;
    private String tel;
    private List<ImageVo> img;
    private LocalDateTime assigned_date;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;
}
