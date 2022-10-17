package com.examination.controller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.examination.bean.*;
import com.examination.service.AnswerService;
import com.examination.service.PaperDetailsService;
import com.examination.service.PaperDetailsVMService;
import com.examination.utils.GlobalUserUtil;
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
    @Autowired
    private AnswerService answerService;

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
        model.addAttribute("status",0);
        return "user/paper_details";
    }

    /**
     * 下一题 上一题
     * 回显下一题或上一题
     *
     * @param req
     * @return
     */
    @ResponseBody
    @PostMapping("/paperDetails")
    public Object InDetails(@RequestBody String req){

        Integer pId = Integer.parseInt((String) JSONObject.parseObject(req).get("pid"));
        Integer currentNum = Integer.parseInt((String) JSONObject.parseObject(req).get("num")); // 当前题号
        Integer numNext = Integer.parseInt((String) JSONObject.parseObject(req).get("numNext")); // 需要显示的下一题或上一题的题号
        Integer doStatus = Integer.parseInt((String) JSONObject.parseObject(req).get("isNew"));
        String single = (String) JSONObject.parseObject(req).getString("answer");
        String more ="";
        JSONArray jsonArray = JSON.parseObject(req).getJSONArray("answerList");
        for (int i = 0; i < jsonArray.size(); i++){
            more = more + jsonArray.get(i) +"";
        }

        log.info(more);

        PaperDetailsVM paperDetailsVM = paperDetailsVMService.getOneByPIdAndNum(pId , numNext); // 下一道或上一道题的内容
        QuestionObject questionObject = JSONObject.parseObject(paperDetailsVM.getContent(), QuestionObject.class);

        PaperDetailsVM p = paperDetailsVMService.getOneByPIdAndNum(pId,currentNum); // 查本题
        if (doStatus == 0){   // 直接存入数据库
            // 将本题存入数据库
            Answer answer = new Answer();
            answer.setPdId(p.getPdId()); // 本题的pdId
            // 单选
            if (!"".equals(single) && "".equals(more)){
                answer.setChecked(single);
                answer.setUser_id(GlobalUserUtil.getUser().getId());
                if(single.equals(p.getCorrect())){
                    answer.setValue(p.getScore());
                }else{
                    answer.setValue(0);
                }
                // 将本题存入数据库
                answerService.save(answer);
            }else if ("".equals(single) && !"".equals(more)) { // 多选
                answer.setChecked(more);
                answer.setUser_id(GlobalUserUtil.getUser().getId());
                if (more.equals(paperDetailsVM.getCorrect())) {
                    answer.setValue(paperDetailsVM.getScore());
                } else {
                    answer.setValue(0);
                }
                answerService.save(answer);
            }
        }else if (doStatus == 1){
            // 将本题更新
            Integer value = 0;
            LambdaUpdateWrapper<Answer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Answer::getPdId,p.getPdId());
            if (!"".equals(single) && "".equals(more)){
                if(single.equals(paperDetailsVM.getCorrect())){
                     value = p.getScore();
                }else{
                    value = 0;
                }
                lambdaUpdateWrapper.set(Answer::getChecked,single).set(Answer::getValue,value);
                answerService.update(lambdaUpdateWrapper);
            }else if ("".equals(single) && !"".equals(more)){
                if (more.equals(paperDetailsVM.getCorrect())) {
                   value = p.getScore();
                } else {
                    value = 0;
                }
                lambdaUpdateWrapper.set(Answer::getChecked,more).set(Answer::getValue,value);
                answerService.update(lambdaUpdateWrapper);
            }
        }

        // 判断下一题或上一题是否已做
        Integer isDo = 0;
        String checked = answerService.ishave(paperDetailsVM.getPdId());
        if (!"".equals(checked) && checked != null){
            isDo = 1;
        }

        HashMap<String,Object> rep = new HashMap<>();
        rep.put("status",isDo);
        rep.put("questionType",paperDetailsVM.getQuestionType());
        rep.put("num",numNext);
        rep.put("questionObject",questionObject);
        return rep;
    }

}
