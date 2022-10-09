package com.examination.controller;

import com.examination.service.QuestionService;
import com.examination.utils.GlobalUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 填空题
 * @Author:晓风残月Lx
 * @Date: 2022/10/7 17:35
 */
@Controller
@Slf4j
public class CompletionController {

    @Autowired
    private QuestionService questionService;


    @RequestMapping("/admin/completionEditors")
    public String tocompletionPage(){
        return "admin/nestable";
    }

    @RequestMapping("/admin/completions")
    public String completions(HttpServletRequest request){
        questionService.insertCompletionQuestion(request, GlobalUserUtil.getUser().getUserName());
        return "admin/nestable";
    }


}
