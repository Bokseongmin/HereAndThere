package com.mz.hat.support;

import static com.mz.hat.support.MspUtil.makeResult;

import com.mz.hat.support.annotation.MSP;
import com.mz.hat.support.result.MspResult;
import com.mz.hat.support.result.MspStatus;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = {MSP.class})
public class MspHandler {

    @ExceptionHandler
    public ResponseEntity<MspResult> globalExceptionHandler(Exception e) {
        logger.error("Exception occurred.", e);
        HashMap<String, String> errMsg = new HashMap<>();
        errMsg.put("error_msg", e.getMessage());
        MspResult result = makeResult(MspStatus.ERROR, errMsg);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}