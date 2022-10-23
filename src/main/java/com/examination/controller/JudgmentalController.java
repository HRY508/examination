package com.examination.controller;

import com.examination.service.QuestionService;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.StaticVariableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 判断题
 * @Author:晓风残月Lx
 * @Date: 2022/10/7 14:04
 */
@Controller
public class JudgmentalController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/admin/judgmentalEditors")
    public String tojudgmentalPage(){
        return "admin/tree_view";
    }

    @RequestMapping("/admin/judgmentals")
    public String judgmentals(HttpServletRequest request, Model model){
        questionService.insertJudgeQuestion(request, GlobalUserUtil.getUser().getUserName());
        // 显示管理员已登录
        model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "admin/tree_view";
    }


}
