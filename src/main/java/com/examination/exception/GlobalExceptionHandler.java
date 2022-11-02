package com.examination.exception;


import com.examination.utils.GlobalUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 全局异常处理器
 * @Author he
 * @Data 2022/11/01 21:27
 */
@ControllerAdvice//与@Exceptionhandler配合使用实现全局异常处理
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
        //（Exception.class）选择捕获的异常
        @ExceptionHandler(Exception.class)
        public Object handleException(Exception e, HttpServletRequest request) {
            Map<String, Object> map = new HashMap<>();
            //根据当前处理的异常，自定义的错误数据
            map.put("code", 500);
            map.put("message", GlobalExceptionHandler.getExceptionDetail(e));
            //将详细的错误信息写入日志
            logger.error("用户:"+ GlobalUserUtil.getUser().getUserName()+"操作时发生了异常："+e);
            logger.error("用户信息："+ GlobalUserUtil.getUser());
            logger.error("该发生异常URL--->"+ request.getRequestURL());
            logger.error("异常跟踪栈----->："+ GlobalExceptionHandler.getExceptionDetail(e));
            return map;
        }
    //用来获取异常的详细信息
    public static String getExceptionDetail(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        ex.printStackTrace(pout);
        String exceptionDetail = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception e) {
        }
        return  exceptionDetail;
    }

}