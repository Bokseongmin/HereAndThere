package com.mz.hat.support;

import com.mz.hat.support.result.MspHead;
import com.mz.hat.support.result.MspResult;
import com.mz.hat.support.result.MspStatus;

public class MspUtil {

    public static MspResult makeResult(MspStatus status, Object data){
        return MspResult.builder()
            .head(MspHead.builder().result_code(status.getCode()).result_msg(status.getMsg()).build())
            .body(data)
            .build();
    }

    public static MspResult makeResult(String resultCode, String resultMsg,  Object data){
        return MspResult.builder()
            .head(MspHead.builder().result_code(resultCode).result_msg(resultMsg).build())
            .body(data)
            .build();
    }
}
