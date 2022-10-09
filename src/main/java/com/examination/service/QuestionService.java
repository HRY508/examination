package com.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.Question;
import com.examination.bean.QuestionVM;

import javax.servlet.http.HttpServletRequest;

public interface QuestionService extends IService<Question> {
    Question insertSelectQuestion(HttpServletRequest request, String userName,Integer questionType);

    Question insertJudgeQuestion(HttpServletRequest request, String userName);

    int updateSelectQuestion(HttpServletRequest request,String userName);

    Question insertCompletionQuestion(HttpServletRequest request, String userName);
}
