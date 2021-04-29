package com.woniu.exception;

import com.woniu.util.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/29/10:52
 * @Description: 全局异常类
 */
@ControllerAdvice
public class ControllerException {
    @ExceptionHandler({Exception.class})
    public ResponseResult HandlerException() {
        return ResponseResult.FAIL;
    }
}
