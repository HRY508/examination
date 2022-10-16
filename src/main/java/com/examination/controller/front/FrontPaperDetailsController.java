package com.examination.controller.front;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.examination.bean.PaperDetails;
import com.examination.bean.PaperDetailsVM;
import com.examination.bean.QuestionItemObject;
import com.examination.bean.QuestionObject;
import com.examination.service.PaperDetailsService;
import com.examination.service.PaperDetailsVMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/14 9:32
 */
@RequestMapping("/user/paper")
@Controller
@Slf4j
public class FrontPaperDetailsController {

    @Autowired
    private PaperDetailsVMService paperDetailsVMService;

    @RequestMapping("/paperDetail")
    public String toPaperDetails(@RequestParam(value = "pid",defaultValue = "0",required = false)Integer pId,
                                 Model model){
        if(pId == null || pId == 0){
            return "error/4xx";
        }
        Integer num = 1;
        PaperDetailsVM paperDetailsVM = paperDetailsVMService.getOneByPIdAndNum(pId , num);
        QuestionObject questionObject = JSONObject.parseObject(paperDetailsVM.getContent(), QuestionObject.class);

        model.addAttribute("questionType",paperDetailsVM.getQuestionType());
        model.addAttribute("singleNum",paperDetailsVM.getSingleSelect());
        model.addAttribute("moreNum",paperDetailsVM.getMoreSelect());
        model.addAttribute("pId",pId);
        model.addAttribute("num",num);
        model.addAttribute("questionObject",questionObject);
        return "user/paper_details";
    }

    @ResponseBody
    @PostMapping("/paperDetails")
    public Object InDetails(@RequestBody String req){

        Integer pId = (Integer) JSONObject.parseObject(req).get("pid");
        Integer num = (Integer) JSONObject.parseObject(req).get("num");
        String answer = (String) JSONObject.parseObject(req).get("answer");

        PaperDetailsVM paperDetailsVM = paperDetailsVMService.getOneByPIdAndNum(pId , num);
        QuestionObject questionObject = JSONObject.parseObject(paperDetailsVM.getContent(), QuestionObject.class);
        HashMap<String,Object> rep = new HashMap<>();
        rep.put("questionType",paperDetailsVM.getQuestionType());
        rep.put("singleNum",paperDetailsVM.getSingleSelect());
        rep.put("moreNum",paperDetailsVM.getMoreSelect());
        rep.put("pId",pId);
        rep.put("num",num);
        rep.put("questionObject",questionObject);
        return rep;
    }

}
