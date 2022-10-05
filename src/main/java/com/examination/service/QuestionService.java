package com.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.Question;
import com.examination.bean.QuestionVM;

import javax.servlet.http.HttpServletRequest;

public interface QuestionService extends IService<Question> {
    Question insertQuestion(HttpServletRequest request, String userName);
}
