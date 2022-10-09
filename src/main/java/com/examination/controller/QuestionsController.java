package com.examination.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.*;
import com.examination.mapper.QuestionVMMapper;
import com.examination.service.ContentService;
import com.examination.service.QuestionEditVMService;
import com.examination.service.QuestionService;
import com.examination.service.QuestionVMService;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.StaticVariableUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Description
 * @Author he
 * @Data 2022/10/5 15:50
 */

@Controller
public class QuestionsController {

    @Autowired
    private QuestionVMService questionVMService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private QuestionVMMapper questionVMMapper;

    @Autowired
    private QuestionEditVMService questionEditVMService;

    //查询
    @RequestMapping("/admin/questionsList")
    public String questionView(HttpServletRequest request, Model model,
                               @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                               @RequestParam(required = false, defaultValue = "0", value = "name") String questionName,
                               @RequestParam(required = false,defaultValue = "0",value = "type") Integer questionType){
        System.out.println("参数pn------"+pn+"参数questionName---"+questionName+"参数---"+questionType);
        //默认显示第1页，显示5个数据
        Page page = new Page(pn,5);

        Page<QuestionVM> result = null;

        //未选择题型，未输入题目关键字，进行全部查询
        if((questionName.equals("null")||questionName.equals("0"))&&questionType.equals(0))
        {
            System.out.println("未选择题型，未输入题目关键字，进行全部查询");
            result = questionVMService.getQuestionList(page);
        }

        //进行了题目关键字搜索而没进行题型检索
        else if((!questionName.equals("0"))&&(questionType.equals(0)))
        {
            System.out.println("进行了题目关键字搜索而没进行题型检索");
            result = questionVMService.selectByQuestionName(page, questionName);
        }

        //进行了题型检索而没进行题目关键字检索
        else if((!questionType.equals(0))&&(questionName.equals("null")))
        {
            System.out.println("进行了题型检索而没进行题目关键字检索");
            result = questionVMService.selectByQuestionType(page, questionType);
        }

        //进行了题目关键字检索和题型检索
        else
        {
            System.out.println("进行了题目关键字检索和题型检索");
            result = questionVMService.selectByConditionQuestionVM(page, questionType, questionName);
        }

        //获取Content中的标题
        List<QuestionVM> records = result.getRecords();
        for(int i=0;i<records.size();i++)
        {
            QuestionObject questionObject = null;
            questionObject = JSON.parseObject(records.get(i).getContent(), QuestionObject.class);
            records.get(i).setContent(questionObject.getTitleContent());
        }

        //设置model、返回视图
        model.addAttribute("questionList",records);
        request.setAttribute("jumpUrl","/admin/questionsList?pn=");
        request.setAttribute("typeUrl","/admin/questionsList?type=");
        request.setAttribute("nameUrl","/admin/questionsList?name=");
        request.setAttribute("qType","&type=");
        request.setAttribute("qTypeValue",questionType);
        request.setAttribute("qName","&name=");
        if(questionName.equals("null")||questionName.equals("0"))
        {
            request.setAttribute("qNameValue",null);
        }
        else {
            request.setAttribute("qNameValue",questionName);
        }
        request.setAttribute("page",result);
        return "admin/questions_list";
    }


    // 修改状态
    @ResponseBody
    @PostMapping("/admin/questionStatusChange")
    public Object statusChange(@RequestBody String req){
        User u = new User();
        //转化为json数据
        JSONObject jsonObject = (JSONObject) JSONObject.parse(req);
        //获取点击前的按钮状态，该修改的id
        Integer id = Integer.parseInt(((String) jsonObject.get("id")));
        //转换为Integer
        Integer qStatus = (Integer) jsonObject.get("qStatus");
        qStatus = qStatus==0?1:0;
        UpdateWrapper<Question> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("status",qStatus);
        boolean update = questionService.update(wrapper);
        if (update){
            Map<String, Object> rep = new HashMap<String, Object>();
            rep.put("code", 200);
            rep.put("status",qStatus);//返回按钮状态，前端根据状态修改按钮的文本内容
            return rep;
        }
        else{
            return null;
        }
    }

    //批量删除
    // 批量删除
    @RequestMapping("admin/deleteQuestionsCounts")
    public String deleteCount(@RequestParam String ids){//ids是复选框名字
        System.out.println("进入了删除"+ids);
        List<String> delList = new ArrayList<>();
        String[] strs = ids.split(",");
        for (String str : strs) {
            delList.add(str);
        }
        boolean b = questionService.removeByIds(delList);
        boolean b1 = contentService.removeByIds(delList);
        System.out.println("删除-----------------------------"+b);
        return "redirect:/admin/questionsList";
    }

    //删除
    @GetMapping("admin/deleteQuestion/{id}")
    public String deleteQuestion(@PathVariable Integer id) {
        questionService.removeById(id);
        contentService.removeById(id);
        return "redirect:/admin/questionsList";
    }


    //更新
    @RequestMapping("/admin/updateQuestion")
    public String updateSingleQuestion(HttpServletRequest request){
        System.out.println(request.getParameterValues("id")[0]);
        Integer id = Integer.parseInt(request.getParameterValues("id")[0]);
        QuestionEditVM questionEditVM = new QuestionEditVM();
        questionEditVM.setId(id);
        Integer questionType = Integer.parseInt(request.getParameter("questionType"));
        questionEditVM.setQuestionType(questionType);
        System.out.println("类型+++++++++++++"+questionType);
        if(questionType == StaticVariableUtil.singleSelectType){
            questionEditVM.setCorrect(request.getParameterValues("correct")[0]);
        }else if(questionType == StaticVariableUtil.moreSelectType){
            String[] corrects = request.getParameterValues("correct");
            questionEditVM.setCorrect(StringUtils.join(corrects));
        }
        else if (questionType == StaticVariableUtil.JudgmentalType){
            questionEditVM.setCorrect(request.getParameterValues("correct")[0]);
        }
        questionEditVM.setScore(Integer.parseInt(request.getParameter("score")));
        questionEditVM.setDifficult(Integer.parseInt(request.getParameter("difficult")));
        questionEditVM.setCreateUser(GlobalUserUtil.getUser().getUserName());
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
        questionEditVM.setContent(selectContents);
        int i = questionEditVMService.updateQuestion(questionEditVM);
        return "redirect:/admin/questionsList";
    }


}
