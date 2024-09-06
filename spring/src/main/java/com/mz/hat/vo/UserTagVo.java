package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserTagVo {
    private int id;
    private int tag_id;
    private int tour_id;
    private Integer user_id;
    private String ip_address;
    private LocalDateTime regdate;
}
