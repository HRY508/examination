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
import org.springframework.transaction.annotation.Transactional;

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
    private ContentService contentService;

    /**
     * 单选和多选
     * @param request
     * @param userName
     * @param questionType
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Question insertSelectQuestion(HttpServletRequest request, String userName,Integer questionType) {
        Question question = new Question();
        question.setQuestionType(questionType);
        question.setDifficult(Integer.parseInt(request.getParameterValues("difficult")[0]));
        Integer questionPool = Integer.parseInt(request.getParameterValues("questionPoolValue")[0]);
        question.setQuestionPool(questionPool);
        if(questionType == StaticVariableUtil.singleSelectType){
            question.setCorrect(request.getParameterValues("correct")[0]);
        }else if(questionType == StaticVariableUtil.moreSelectType){
            String[] corrects = request.getParameterValues("correct");
            question.setCorrect(StringUtils.join(corrects));
        }
        question.setScore( Integer.parseInt(request.getParameterValues("score")[0]));
        question.setCreateUser(userName);
        question.setStatus(StaticVariableUtil.status);

        boolean save = questionService.save(question);
        Integer id = question.getId();
        //动态获取题的详细信息插入到表t_content中
        String param[] = {"A","B","C","D","E","F","G","H","I","J"};
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

    /**
     * 判断题
     * @param request
     * @param userName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Question insertJudgeQuestion(HttpServletRequest request, String userName) {

        Question question=new Question();
        question.setQuestionType(StaticVariableUtil.JudgmentalType);
        Integer questionPool = Integer.parseInt(request.getParameterValues("questionPoolValue")[0]);
        question.setQuestionPool(questionPool);
        question.setDifficult(Integer.parseInt(request.getParameter("difficult")));
        question.setCorrect(request.getParameterValues("correct")[0]);
        question.setCreateUser(userName);
        question.setScore(Integer.parseInt(request.getParameter("score")));
        question.setCreateUser(userName);
        question.setStatus(StaticVariableUtil.status);

        boolean save = questionService.save(question);
        Integer id = question.getId();

        //动态获取题的详细信息插入到表t_content中
        String param[] = {"√","×"};

        QuestionObject questionObject = new QuestionObject();
        List<QuestionItemObject> list = new ArrayList<>();
        Enumeration parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
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

    /**
     * 添加填空题
     * @param request
     * @param userName
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Question insertCompletionQuestion(HttpServletRequest request, String userName) {
        Question question=new Question();
        question.setCorrect(request.getParameter("correct"));
        Integer questionPool = Integer.parseInt(request.getParameterValues("questionPoolValue")[0]);
        question.setQuestionPool(questionPool);
        question.setDifficult(Integer.parseInt(request.getParameter("difficult")));
        question.setScore(Integer.parseInt(request.getParameter("score")));
        question.setQuestionType(StaticVariableUtil.CompletionType);
        question.setStatus(StaticVariableUtil.status);
        question.setCreateUser(userName);
        boolean save = questionService.save(question);
        Integer id = question.getId();
        QuestionObject questionObject=new QuestionObject();
        questionObject.setTitleContent(request.getParameterValues("cotent")[0]);
        questionObject.setAnalyze(request.getParameterValues("analysis")[0]);
        String selectContents = JSON.toJSONString(questionObject);
        Content content=new Content();
        content.setContent(selectContents);
        // 保证主键一致性
        content.setId(id);
        BaseMapper<Content> contentMapper = contentService.getBaseMapper();
        int i = contentMapper.insert(content);
        return question;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSelectQuestion(HttpServletRequest request, String userName) {
        return 0;
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int updateSelectQuestion(HttpServletRequest request, String userName) {
//        Question question=new Question();
//        question.setQuestionType(StaticVariableUtil.singleSelectType);
//        question.setDifficult(Integer.parseInt(request.getParameter("difficult")));
//        question.setCorrect(request.getParameterValues("correct")[0]);
//        question.setCreateUser(userName);
//        question.setScore(Integer.parseInt(request.getParameter("score")));
//        question.setCreateUser(userName);
//        question.setStatus(StaticVariableUtil.status);
//
//        boolean save = questionService.save(question);
//        Integer id = question.getId();
//
//        //动态获取题的详细信息插入到表t_content中
//        String param[] = {"√","×"};
//        HashMap<String,Object> map = new HashMap<>();
//        QuestionObject questionObject = new QuestionObject();
//        List<QuestionItemObject> list = new ArrayList<>();
//        Enumeration parameterNames = request.getParameterNames();
//        while(parameterNames.hasMoreElements()){
//            String name = (String) parameterNames.nextElement();
//            String value = request.getParameter(name);
//            for (int i =0;i<param.length;i++){
//                if(name.equals(param[i]))
//                {
//                    QuestionItemObject object = new QuestionItemObject();
//                    object.setPrefix(param[i]);
//                    object.setContent(request.getParameter(param[i]));
//                    list.add(object);
//                }
//            }
//        }
//        //设置题的题目、解析、正文（先转换为Json格式再存入数据库）
//        questionObject.setTitleContent(request.getParameterValues("content")[0]);
//        questionObject.setAnalyze(request.getParameterValues("analysis")[0]);
//        questionObject.setQuestionItemObjects(list);
//        String selectContents = JSON.toJSONString(questionObject);
//        Content content1 = new Content();
//        content1.setContent(selectContents);
//        //保证主键一致性
//        content1.setId(id);
//        BaseMapper<Content> contentMapper = contentService.getBaseMapper();
//        System.out.println("正文内容content"+content1);
//        int insert2 = contentMapper.insert(content1);
//
//        return question;
//    }

}
