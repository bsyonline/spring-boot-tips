package com.rolex.tips.exception;

import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AsyncTimeoutException.class)
    @ResponseBody
    public String timeout(){
        log.info("");
        return "timeout";
    }
}
