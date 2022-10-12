package com.examination.controller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.Content;
import com.examination.bean.Question;
import com.examination.bean.QuestionItemObject;
import com.examination.bean.QuestionObject;
import com.examination.service.ContentService;
import com.examination.service.QuestionEditVMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("/questionDetail/{id}")
    public String toQuestionDetail(@PathVariable("id")Integer id, Model model){
        QueryWrapper<Content> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        Content question = contentService.getOne(wrapper);
        String contentStr  = question.getContent();
        QuestionObject questionObject = JSON.parseObject(contentStr, QuestionObject.class);
        questionObject.setAnalyze("");
        questionObject.setCorrect("");
        model.addAttribute("qId",question.getId());
        model.addAttribute("questionObject",questionObject);
        return "user/question_detail";
    }
    @ResponseBody
    @RequestMapping("/questionDetails")
    public Object lnQuestion(@RequestBody String req){
        //转化为json数据
        JSONObject jsonObject = (JSONObject) JSONObject.parse(req);
        //获取点击前的按钮状态，该修改的id
        Integer qId = (Integer) jsonObject.get("id");
        QueryWrapper<Content> wrapper = new QueryWrapper<>();
        wrapper.eq("id", qId);
        Content question = contentService.getOne(wrapper);
        String contentStr  = question.getContent();
        QuestionObject questionObject = JSON.parseObject(contentStr, QuestionObject.class);
        List<QuestionItemObject> list = new ArrayList<>();
        HashMap<String,Object> rep = new HashMap<>();
        rep.put("id",qId);
        rep.put("questionObject",questionObject);
        rep.put("list",list);
        return rep;
    }
}
