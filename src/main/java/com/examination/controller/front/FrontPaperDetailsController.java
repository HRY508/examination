package com.examination.controller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.examination.bean.*;
import com.examination.service.AnswerService;
import com.examination.service.PaperDetailsVMService;
import com.examination.service.ScoreService;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.LocalDateUtil;
import com.examination.utils.StaticVariableUtil;
import com.examination.viewmodel.PaperDetailsVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    private ScoreService scoreService;

    @ResponseBody
    @RequestMapping("/isScore")
    public Object isScore(@RequestBody String req){

        Integer pId = (Integer) JSONObject.parseObject(req).get("pId");
        Map<String,Integer> map = new HashMap<>();
        int mark = scoreService.selectByPId(pId, GlobalUserUtil.getUser().getId());
        if(mark!=0){
            map.put("code", StaticVariableUtil.FAILCODE);
            map.put("score", mark);
        }else {
            map.put("code",StaticVariableUtil.SUCCESSCODE);
        }
        return map;
    }


    @RequestMapping("/paperDetail")
    public String toPaperDetails(@RequestParam(value = "pid",defaultValue = "0",required = false)Integer pId,
                                 Model model){
        if(pId == null || pId == 0){
            return "error/5xx";
        }

        Integer num = 1;
        PaperDetailsVM paperDetailsVM = paperDetailsVMService.getOneByPIdAndNum(pId , num);
        QuestionObject questionObject = JSONObject.parseObject(paperDetailsVM.getContent(), QuestionObject.class);

        // 判断本题是否已做
        Integer isDo = 0;
        String checked = answerService.ishave(paperDetailsVM.getPdId());
        if (!"".equals(checked) && checked != null){
            isDo = 1;
            model.addAttribute("checked",checked);
        }


        // 转换时间格式 将结束时间转换为字符串
        String endTime = LocalDateUtil.localDateToString(paperDetailsVM.getEndTime());

        model.addAttribute("endTime",endTime);
        model.addAttribute("questionType",paperDetailsVM.getQuestionType());
        model.addAttribute("singleNum",paperDetailsVM.getSingleSelect());
        model.addAttribute("moreNum",paperDetailsVM.getMoreSelect());
        model.addAttribute("pId",pId);
        model.addAttribute("num",num);
        model.addAttribute("questionObject",questionObject);
        model.addAttribute("status",isDo);
        // 显示用户已登录
        model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "user/paper_details";
    }

    /**
     * 下一题 上一题
     * 回显下一题或上一题
     *
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
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
        Integer isSaveAndUpdate = 0; // 是否添加和更新

        PaperDetailsVM paperDetailsVM = paperDetailsVMService.getOneByPIdAndNum(pId , numNext); // 下一道或上一道题的内容
        QuestionObject questionObject = JSONObject.parseObject(paperDetailsVM.getContent(), QuestionObject.class);

        PaperDetailsVM p = paperDetailsVMService.getOneByPIdAndNum(pId,currentNum); // 查本题
        if (doStatus == 0){   // 直接存入数据库
            // 将本题存入数据库
            Answer answer = new Answer();
            answer.setPdId(p.getPdId()); // 本题的pdId
            answer.setPId(pId);
            if (single != null && !"".equals(single) && "".equals(more)){// 单选
                answer.setChecked(single);
                answer.setUserId(GlobalUserUtil.getUser().getId());
                if(single.equals(p.getCorrect())){
                    answer.setValue(p.getScore());
                }else{
                    answer.setValue(0);
                }
                // 将本题存入数据库
                answerService.save(answer);
                isSaveAndUpdate = 1;
            }else if ("".equals(single) && !"".equals(more)) { // 多选
                answer.setChecked(more);
                answer.setUserId(GlobalUserUtil.getUser().getId());
                if (more.equals(p.getCorrect())) {
                    answer.setValue(p.getScore());
                } else {
                    answer.setValue(0);
                }
                answerService.save(answer);
                isSaveAndUpdate = 1;
            }
        }else if (doStatus == 1){
            // 将本题更新
            Integer value = 0;
            LambdaUpdateWrapper<Answer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Answer::getPdId,p.getPdId());

            if (single != null && !"".equals(single) && "".equals(more)){ //单选
                log.info("========="+single);
                log.info("========="+more);
                if(single.equals(p.getCorrect())){
                     value = p.getScore();
                }else{
                    value = 0;
                }
                lambdaUpdateWrapper.set(Answer::getChecked,single).set(Answer::getValue,value);
                answerService.update(lambdaUpdateWrapper);
                isSaveAndUpdate = 1;
            }else if ("".equals(single) && !"".equals(more)){ // 多选
                if (more.equals(p.getCorrect())) {
                   value = p.getScore();
                } else {
                    value = 0;
                }
                lambdaUpdateWrapper.set(Answer::getChecked,more).set(Answer::getValue,value);
                answerService.update(lambdaUpdateWrapper);
                isSaveAndUpdate = 1;
            }else if ((single == null || "".equals(single)) && "".equals(more)) { // 什么都没选
                answerService.remove(lambdaUpdateWrapper);
                isSaveAndUpdate = 0;
            }
        }

        HashMap<String,Object> rep = new HashMap<>();

        // 判断下一题或上一题是否已做
        Integer isDo = 0;
        String checked = answerService.ishave(paperDetailsVM.getPdId());
        if (!"".equals(checked) && checked != null){
             isDo = 1;
             rep.put("checked",checked);
        }

        rep.put("isSave",isSaveAndUpdate);
        rep.put("status",isDo);
        rep.put("questionType",paperDetailsVM.getQuestionType());
        rep.put("num",numNext);
        rep.put("questionObject",questionObject);
        return rep;
    }


    /**
     * 提交
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    @PostMapping("/paperFinish")
    public Object finishExam(@RequestBody String req){
        Integer userId = GlobalUserUtil.getUser().getId();

        Integer pId = Integer.parseInt((String) JSONObject.parseObject(req).get("pid"));
        Integer currentNum = Integer.parseInt((String) JSONObject.parseObject(req).get("num")); // 当前题号
        Integer doStatus = Integer.parseInt((String) JSONObject.parseObject(req).get("isNew"));
        String single = (String) JSONObject.parseObject(req).getString("answer");
        String more ="";
        JSONArray jsonArray = JSON.parseObject(req).getJSONArray("answerList");
        for (int i = 0; i < jsonArray.size(); i++){
            more = more + jsonArray.get(i) +"";
        }

        PaperDetailsVM p = paperDetailsVMService.getOneByPIdAndNum(pId,currentNum); // 查本题
        if (doStatus == 0){ // 未做过
            Answer answer = new Answer();
            answer.setPId(pId);
            answer.setPdId(p.getPdId()); // 本题的pdId
            // 单选
            if ((!"".equals(single) && single != null) && "".equals(more)){
                answer.setChecked(single);
                answer.setUserId(GlobalUserUtil.getUser().getId());
                if(single.equals(p.getCorrect())){
                    answer.setValue(p.getScore());
                }else{
                    answer.setValue(0);
                }
                // 将本题存入数据库
                answerService.save(answer);
            }else if (("".equals(single) || single == null) && !"".equals(more)) { // 多选
                answer.setChecked(more);
                answer.setUserId(GlobalUserUtil.getUser().getId());
                if (more.equals(p.getCorrect())) {
                    answer.setValue(p.getScore());
                } else {
                    answer.setValue(0);
                }
                answerService.save(answer);
            }
        }else if (doStatus == 1){ // 已做过
            // 将本题更新
            Integer value = 0;
            LambdaUpdateWrapper<Answer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Answer::getPdId,p.getPdId());
            if (!"".equals(single) && "".equals(more)){
                if(single.equals(p.getCorrect())){
                    value = p.getScore();
                }else{
                    value = 0;
                }
                lambdaUpdateWrapper.set(Answer::getChecked,single).set(Answer::getValue,value);
                answerService.update(lambdaUpdateWrapper);
            }else if ("".equals(single) && !"".equals(more)){
                if (more.equals(p.getCorrect())) {
                    value = p.getScore();
                } else {
                    value = 0;
                }
                lambdaUpdateWrapper.set(Answer::getChecked,more).set(Answer::getValue,value);
                answerService.update(lambdaUpdateWrapper);
            }
        }
        // 总得分
        Integer totalScore = answerService.getTotalScore(pId , userId);
        // 存入得分表
        Score score = new Score();
        score.setMark(totalScore);
        score.setPId(pId);
        score.setUserId(userId);
        score.setExamTime(LocalDateTime.now());
        boolean save = scoreService.save(score);

        HashMap<String,Object> rep = new HashMap<>();
        rep.put("isCheck" , 0);
        rep.put("score",totalScore);
        if (save){
            rep.put("isCheck" , 1);
        }
        return rep;
    }

}
