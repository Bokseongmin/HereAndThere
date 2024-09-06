package com.mz.hat.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReplyVo {
    private int id;
    private int entity_id;
    private int user_id;
    private String content;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

    private String user_name;
}
