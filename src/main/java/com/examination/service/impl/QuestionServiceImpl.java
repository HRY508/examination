package com.examination.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.*;
import com.examination.mapper.QuestionMapper;
import com.examination.service.ContentService;
import com.examination.service.QuestionService;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.StaticVariableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**
 * @Description
 * @Author he
 * @Data 2022/10/4 18:31
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private QuestionService questionService;
    @Autowired
    ContentService contentService;

    @Override
    public Question insertQuestion(HttpServletRequest request, String userName) {
        Question question = new Question();
        question.setDifficult(Integer.parseInt(request.getParameterValues("difficult")[0]));
        question.setCorrect(request.getParameterValues("correct")[0]);
        question.setScore( Integer.parseInt(request.getParameterValues("score")[0]));
        question.setCreateUser(GlobalUserUtil.getUser().getUserName());
        question.setStatus(StaticVariableUtil.status);
        BaseMapper<Question> mapper = questionService.getBaseMapper();
        boolean save = questionService.save(question);
        System.out.println("公共属性question"+question);
        System.out.println();
        //
        Integer id = question.getId();
        question.setContentId(id);
        String param[] = {"A","B","C","D","E","F","G","H","I","J"};

        HashMap<String,Object> map = new HashMap<>();
        QuestionObject questionObject = new QuestionObject();
        List<QuestionItemObject> list = new ArrayList<>();
        Enumeration parameterNames = request.getParameterNames();
        String[] pr = {};
        while (parameterNames.hasMoreElements()) {
            String name = (String) parameterNames.nextElement();
            String value = request.getParameter(name);
            for (int i =0;i<param.length;i++){
                if(name.equals(param[i]))
                {
                    QuestionItemObject object = new QuestionItemObject();
                    object.setPrefix(param[i]);
                    object.setContent(request.getParameter(param[i]));
                    list.add(object);
                }
            }
        }
        System.out.println("QuestionItemObjectList"+list);
        questionObject.setTitleContent(request.getParameterValues("content")[0]);
        questionObject.setAnalyze(request.getParameterValues("analysis")[0]);
        questionObject.setQuestionItemObjects(list);
        String content = JSON.toJSONString(questionObject);
        Content content1 = new Content();
        content1.setContent(content);
        content1.setId(id);
        BaseMapper<Content> mapper2 = contentService.getBaseMapper();
        System.out.println("正文内容content"+content1);
        int insert2 = mapper2.insert(content1);
        return question;
    }
}
