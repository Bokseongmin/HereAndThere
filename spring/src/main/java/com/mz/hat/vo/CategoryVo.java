package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CategoryVo {
    private int id;
    private String name;
    private String code;
    private String code_ref;
    private String icon;
}
