package com.examination.controller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.*;
import com.examination.service.ContentService;
import com.examination.service.QuestionDetailsService;
import com.examination.service.QuestionEditVMService;
import com.examination.service.QuestionService;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.StrOperateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description
 * @Author he
 * @Data 2022/10/12 17:12
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class FrontQuestionDetailController {
    @Autowired
    private ContentService contentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionDetailsService questionDetailsService;


    @RequestMapping("/questionDetail/{id}")
    public String toQuestionDetail(@PathVariable("id")Integer id, Model model){
        QueryWrapper<Content> wrapper = new QueryWrapper<>();
        QueryWrapper<Question> wrapper0 = new QueryWrapper<>();
        wrapper.select("content","id").eq("id", id);
        wrapper0.select("difficult","question_type").eq("id",id);
        Question qu = questionService.getOne(wrapper0);
        Integer questionType = qu.getQuestionType();
        Integer difficult = qu.getDifficult();
        Content question = contentService.getOne(wrapper);
        String contentStr  = question.getContent();
        QuestionObject questionObject = JSON.parseObject(contentStr, QuestionObject.class);
        questionObject.setTitleContent(StrOperateUtil.removeTag(questionObject.getTitleContent()));
        model.addAttribute("qId",question.getId());
        model.addAttribute("difficult",difficult);
        model.addAttribute("questionType",questionType);
        model.addAttribute("questionObject",questionObject);
        // ?????????????????????
        model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "user/question_detail";
    }


    @ResponseBody
    @RequestMapping("/questionDetails")
    public Object lnQuestion(@RequestBody String req){
        //?????????json??????
        JSONObject jsonObject = (JSONObject) JSONObject.parse(req);
        //?????????????????????????????????????????????id
        Integer qId = (Integer) jsonObject.get("id");
        //????????????????????????????????????
        QueryWrapper<Question> wrapper0 = new QueryWrapper<>();
        QueryWrapper<Content> wrapper = new QueryWrapper<>();
        wrapper.select("content").eq("id", qId);
        wrapper0.select("difficult","question_type").eq("id",qId);
        Question qu = questionService.getOne(wrapper0);
        Integer questionType = qu.getQuestionType();
        Integer difficult = qu.getDifficult();
        Content question = contentService.getOne(wrapper);
        String contentStr  = question.getContent();
        QuestionObject questionObject = JSON.parseObject(contentStr, QuestionObject.class);
        HashMap<String,Object> rep = new HashMap<>();
        rep.put("difficult",difficult);
        rep.put("questionType",questionType);
        rep.put("id",qId);
        rep.put("questionObject",questionObject);
        return rep;
    }

    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    @RequestMapping("/answerQuestion")
    public Object answerQuestion(@RequestBody String req){
        //?????????json??????
        JSONObject jsonObject = (JSONObject) JSONObject.parse(req);
        //?????????????????????????????????????????????Id
        Integer qId = Integer.parseInt((String) jsonObject.get("qId"));
        String choice = jsonObject.getString("uChoice");
        Integer uId = GlobalUserUtil.getUser().getId();
        //???????????????
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("id",qId);
        String correct = questionService.getOne(wrapper).getCorrect();
        //????????????
        QueryWrapper<Content> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("id",qId);
        Content one = contentService.getOne(wrapper2);
        String analysis = JSONObject.parseObject(one.getContent(), QuestionObject.class).getAnalyze();
        //??????????????????
        QuestionDetails questionDetails = new QuestionDetails();
        questionDetails.setQuestionId(qId);
        questionDetails.setUserId(uId);
        boolean res = correct.equals(choice);
        //????????????????????????????????????,????????????2???????????????1
        HashMap<String,Object> rep = new HashMap<>();
        if (correct.equals(choice)){
            questionDetails.setResult(2);
            rep.put("res",1);
        }
        else {
            questionDetails.setResult(1);
            rep.put("res",0);
        }
        //??????????????????
        QueryWrapper<QuestionDetails> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("user_id",uId).eq("question_id",qId);
        BaseMapper<QuestionDetails> baseMapper = questionDetailsService.getBaseMapper();
        List<QuestionDetails> list = baseMapper.selectList(wrapper1);
        if (list.size()>=1){//????????????
            Integer id = list.get(0).getId();
            questionDetails.setId(id);
            questionDetailsService.updateById(questionDetails);
        }
        else{//?????????
            boolean save = questionDetailsService.save(questionDetails);
        }
        //?????????????????????
        rep.put("analysis",analysis);
        rep.put("correct",correct);
        return rep;
    }
}
