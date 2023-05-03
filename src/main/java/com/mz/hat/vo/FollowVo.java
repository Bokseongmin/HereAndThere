package com.mz.hat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class FollowVo {
    private int following_user_id;
    private int followed_user_id;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;
}
