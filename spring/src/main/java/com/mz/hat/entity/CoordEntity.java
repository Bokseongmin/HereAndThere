package com.mz.hat.entity;

import com.mz.hat.vo.CoordVo;
import com.mz.hat.vo.CourseVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CoordEntity {
    CourseVo courseVo;
    List<CoordVo> coordVos = new ArrayList<>();
}