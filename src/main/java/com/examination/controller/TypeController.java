package com.examination.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.examination.bean.Question;
import com.examination.mapper.QuestionMapper;
import com.examination.service.QuestionService;
import com.examination.utils.StaticVariableUtil;
import com.examination.viewmodel.TypeVM;
import com.examination.service.TypeService;
import com.examination.service.TypeVMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/29 9:41
 */
@Controller
@Slf4j
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeVMService typeVMService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private QuestionService questionService;


    @RequestMapping("/poolList")
    public String poolList(@RequestParam(value = "pn",defaultValue = "1" ,required = false) Integer pn,
                           @RequestParam(value = "qName",defaultValue = "" ,required = false) String searchName,
                           Model model){
        //默认显示第1页，显示5个数据
        Page<TypeVM> page = new Page<TypeVM>(pn,5);

        Page<TypeVM> typePage = null;

        if (searchName.equals("")){
            typePage = typeVMService.selectByPage(page);
        }else {
            typePage = typeVMService.pageBySearchName(page,searchName);
        }

        model.addAttribute("page",typePage);
        model.addAttribute("searchName",searchName);
        model.addAttribute("jumpUrl","/admin/poolList?pn=");
        model.addAttribute("qName","&qName=");

        return "admin/question_pool";
    }

//    @ResponseBody
//    @RequestMapping("/queryQuestion")
//    public Object queryQuestion(@RequestBody String req){
//
//        JSONObject jsonObject = JSONObject.parseObject(req);
//        Integer pool = (Integer) jsonObject.get("qPool");
//
//        List<QuestionVM> questionVMS = questionVMService.selectByPool(pool);
//
//        for(int i=0;i<questionVMS.size();i++)
//        {
//            QuestionObject questionObject = null;
//            questionObject = JSON.parseObject(questionVMS.get(i).getContent(), QuestionObject.class);
//            questionVMS.get(i).setContent(StrOperateUtil.removeTag(questionObject.getTitleContent()));
//        }
//        HashMap<String,Object> map = new HashMap<>();
//
//        map.put("code",200);
//        map.put("questionList",questionVMS);
//        return map;
//    }

    @ResponseBody
    @RequestMapping("/poolList/delete")
    public Object deletePool(@RequestBody String req) {
        JSONObject jsonObject = JSONObject.parseObject(req);
        Integer id = (Integer) jsonObject.get("typeId");
        Integer code = typeService.selectPoolByIdAndDelete(id);
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        return params;
    }



    @ResponseBody
    @RequestMapping("/addPool")
    public Object addPool(){
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Question::getId).eq(Question::getQuestionPool,0);// 0属于没有分类的题目
        List<Question> questionList = questionService.list(queryWrapper);

        Map<String,Object> param = new HashMap<>();
        param.put("code", StaticVariableUtil.SUCCESSCODE);
        param.put("qList",questionList);
        return param;
    }
}
