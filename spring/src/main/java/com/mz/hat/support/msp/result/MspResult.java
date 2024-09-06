package com.mz.hat.support.msp.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
public class MspResult {

    private MspHead head;
    private Object body;
}