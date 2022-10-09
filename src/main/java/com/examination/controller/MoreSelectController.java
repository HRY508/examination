package com.examination.controller;

import com.examination.service.QuestionService;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.StaticVariableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/6 20:37
 */
@Controller
@Slf4j
public class MoreSelectController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/admin/moreEditors")
    public String toMoreChoicePage(){
        return "admin/calendar";
    }

    @RequestMapping("/admin/moreSelect")
    public String moreSelect(HttpServletRequest request){
        Integer questionType = StaticVariableUtil.moreSelectType;
        questionService.insertSelectQuestion(request, GlobalUserUtil.getUser().getUserName(),questionType);
        log.info("===================="+request.getParameter("difficult"));
        return "admin/calendar";
    }
}
