package com.examination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author he
 * @Data 2022/10/3 23:52
 */
@Controller
public class SingleSelect {
    @RequestMapping("/admin/singleSelect")
    public String singleSelect(HttpServletRequest request){//参数不固定，直接把请求拿过来
        Enumeration<String> parameterNames = request.getParameterNames();
        HashMap<String,Object> map = new HashMap<>();
        while(parameterNames.hasMoreElements()){
           String key = parameterNames.nextElement();
           map.put(key,request.getParameter(key));
           System.out.println(key+":"+request.getParameter(key));
        }
        return "admin/index";
    }
}
