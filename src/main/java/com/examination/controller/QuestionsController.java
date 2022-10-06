package com.examination.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.QuestionObject;
import com.examination.bean.QuestionVM;
import com.examination.mapper.QuestionVMMapper;
import com.examination.service.QuestionVMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author he
 * @Data 2022/10/5 15:50
 */

@Controller
public class QuestionsController {

    @Autowired
    private QuestionVMService questionVMService;

    @Autowired
    private QuestionVMMapper questionVMMapper;

    @RequestMapping("/admin/questionsList")
    public String questionView(HttpServletRequest request, Model model,
                               @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                               @RequestParam(required = false,defaultValue = "0",value = "type") Integer type){
        Page page = new Page(pn,5);
        Page<QuestionVM> result = questionVMService.getQuestionList(page);
        List<QuestionVM> records = result.getRecords();
        //获取Content中的标题
        for (int i=0;i<records.size();i++){
            QuestionObject questionObject = null;
            if((!records.get(i).getQuestionType().equals(type))&&type!=0) {
                System.out.println("正在移除"+records.get(i));
                records.remove(i);
                i--;
            }
            else {
                questionObject = JSON.parseObject(records.get(i).getContent(), QuestionObject.class);
                records.get(i).setContent(questionObject.getTitleContent());
            }
        }
        model.addAttribute("questionList",records);
        request.setAttribute("jumpUrl","/admin/questionsList?pn=");
        request.setAttribute("typeUrl","/admin/questionsList?type=");
        request.setAttribute("page",result);
        return "admin/questions_list";
    }

}
