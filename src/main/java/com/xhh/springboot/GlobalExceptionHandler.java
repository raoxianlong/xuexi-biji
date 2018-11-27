package com.xhh.springboot;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> exceptionHandle(){
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("code", 500);
        ret.put("msg", "系统错误，请稍后重试！");
        return ret;
    }



}
