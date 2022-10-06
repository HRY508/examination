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
                               @RequestParam(required = false, defaultValue = "0", value = "name") String questionName,
                               @RequestParam(required = false,defaultValue = "0",value = "type") Integer questionType){
        System.out.println("参数pn------"+pn+"参数questionName---"+questionName+"参数---"+questionType);
        //默认显示第1页，显示5个数据
        Page page = new Page(pn,5);

        Page<QuestionVM> result = null;

        //未选择题型，未输入题目关键字，进行全部查询
        if((questionName.equals("null")||questionName.equals("0"))&&questionType.equals(0))
        {
            System.out.println("未选择题型，未输入题目关键字，进行全部查询");
            result = questionVMService.getQuestionList(page);
        }

        //进行了题目关键字搜索而没进行题型检索
        else if((!questionName.equals("0"))&&(questionType.equals(0)))
        {
            System.out.println("进行了题目关键字搜索而没进行题型检索");
            result = questionVMService.selectByQuestionName(page, questionName);
        }

        //进行了题型检索而没进行题目关键字检索
        else if((!questionType.equals(0))&&(questionName.equals("null")))
        {
            System.out.println("进行了题型检索而没进行题目关键字检索");
            result = questionVMService.selectByQuestionType(page, questionType);
        }
        //进行了题目关键字检索和题型检索
        else
        {
            System.out.println("进行了题目关键字检索和题型检索");
            result = questionVMService.selectByConditionQuestionVM(page, questionType, questionName);
        }

        //获取Content中的标题
        List<QuestionVM> records = result.getRecords();
        for(int i=0;i<records.size();i++)
        {
            QuestionObject questionObject = null;
            questionObject = JSON.parseObject(records.get(i).getContent(), QuestionObject.class);
            records.get(i).setContent(questionObject.getTitleContent());
        }

        //设置model、返回视图
        model.addAttribute("questionList",records);
        request.setAttribute("jumpUrl","/admin/questionsList?pn=");
        request.setAttribute("typeUrl","/admin/questionsList?type=");
        request.setAttribute("nameUrl","/admin/questionsList?name=");
        request.setAttribute("qType","&type=");
        request.setAttribute("qTypeValue",questionType);
        request.setAttribute("qName","&name=");
        if(questionName.equals("null")||questionName.equals("0"))
        {
            request.setAttribute("qNameValue",null);
        }
        else {
            request.setAttribute("qNameValue",questionName);
        }
        request.setAttribute("page",result);
        return "admin/questions_list";
    }

}
