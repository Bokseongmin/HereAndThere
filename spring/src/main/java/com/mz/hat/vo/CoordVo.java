package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CoordVo {
    private int id;
    private int entity_id;
    private BigDecimal lat;
    private BigDecimal lng;
    private LocalDateTime regdate;
}
