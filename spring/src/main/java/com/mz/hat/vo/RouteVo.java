package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
public class RouteVo {
    private int id;
    private int user_id;
    private String tourist_attrs_list;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;
}
