package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class RegionVo {
    private int id;
    private String name;
    private String lat;
    private String lng;
}
