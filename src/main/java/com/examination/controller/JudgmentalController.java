package com.examination.controller;

import com.examination.service.QuestionService;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.StaticVariableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/7 14:04
 */
@Controller
public class JudgmentalController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/admin/judgmentals")
    public String singleSelect(HttpServletRequest request){
        questionService.insertJudgeQuestion(request, GlobalUserUtil.getUser().getUserName());
        return "admin/index";
    }


}
