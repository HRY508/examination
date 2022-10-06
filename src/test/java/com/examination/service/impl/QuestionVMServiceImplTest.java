package com.examination.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.QuestionVM;
import com.examination.service.QuestionVMService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class QuestionVMServiceImplTest {
    @Autowired
    private QuestionVMService questionVMService;
    @Test
    void test(){
        Page page = new Page(1,5);
        Page<QuestionVM> questionList = questionVMService.getQuestionList(page);
        List<QuestionVM> records = questionList.getRecords();
        System.out.println(records);
    }
//    @Test
//    void test2(){
//        QueryQuestionInfModel model = new QueryQuestionInfModel();
//        model.setQuestionType(1);
////        model.setKeyWord("测");
//        Page page = new Page(1,2);
//        Page<QuestionVM> questionList = questionVMService.selectByConditionQuestionVM(page,model);
//        List<QuestionVM> records = questionList.getRecords();
//        System.out.println(questionList);
//    }
}