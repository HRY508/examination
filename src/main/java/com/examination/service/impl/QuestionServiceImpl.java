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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    public Question insertQuestion(HttpServletRequest request, String userName,Integer questionType) {

        Question question = new Question();
        question.setQuestionType(questionType);
        question.setDifficult(Integer.parseInt(request.getParameterValues("difficult")[0]));
        if(questionType == StaticVariableUtil.singleSelectType){
            question.setCorrect(request.getParameterValues("correct")[0]);
        }else if(questionType == StaticVariableUtil.moreSelectType){
            String[] corrects = request.getParameterValues("correct");
            question.setCorrect(StringUtils.join(corrects));
        }
        question.setScore( Integer.parseInt(request.getParameterValues("score")[0]));
        question.setCreateUser(GlobalUserUtil.getUser().getUserName());
        question.setStatus(StaticVariableUtil.status);

        boolean save = questionService.save(question);
        Integer id = question.getId();
        question.setContentId(id);

        //动态获取题的详细信息插入到表t_content中
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
        //设置题的题目、解析、正文（先转换为Json格式再存入数据库）
        questionObject.setTitleContent(request.getParameterValues("content")[0]);
        questionObject.setAnalyze(request.getParameterValues("analysis")[0]);
        questionObject.setQuestionItemObjects(list);
        String selectContents = JSON.toJSONString(questionObject);
        Content content1 = new Content();
        content1.setContent(selectContents);
        //保证主键一致性
        content1.setId(id);
        BaseMapper<Content> contentMapper = contentService.getBaseMapper();
        System.out.println("正文内容content"+content1);
        int insert2 = contentMapper.insert(content1);
        return question;
    }
}
