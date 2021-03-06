package com.wht.springcloud.fangchanwang.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler({Exception.class,RuntimeException.class})
    public String error500(HttpServletRequest request,Exception e){
        logger.error(e.getMessage(),e);
        logger.error(request.getRequestURL()+"  counter 500");

        return "error/500";
    }
}
