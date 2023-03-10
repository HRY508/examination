package com.examination.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.*;
import com.examination.mapper.QuestionVMMapper;
import com.examination.service.*;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.StaticVariableUtil;
import com.examination.utils.StrOperateUtil;
import com.examination.viewmodel.QuestionEditVM;
import com.examination.viewmodel.QuestionVM;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    private TypeVMService typeVMService;

    @Autowired
    private QuestionEditVMService questionEditVMService;

    @Autowired
    private TypeService typeService;

    //查询
    @RequestMapping("/admin/questionsList")
    public String questionView( Model model,
                               @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                               @RequestParam(required = false, defaultValue = "", value = "name") String questionName,
                               @RequestParam(required = false, defaultValue = "0",value = "qType") Integer questionType,
                               @RequestParam(required = false, defaultValue = "0",value = "qPool") Integer questionPool){

        //默认显示第1页，显示5个数据
        Page<QuestionVM> page = new Page<QuestionVM>(pn,5);

        Page<QuestionVM> result = null;
        //未选择题型，未输入题目关键字，未选择题目种类进行全部查询000
        if(questionName.equals("")&&questionType==0&&questionPool==0)
        {
            System.out.println("未选择题型，未输入题目关键字，未选择题目种类进行全部查询");
            result = questionVMService.getQuestionList(page);
        }

        //进行了题目关键字搜索而没进行题型、题目种类检索
        else if((!questionName.equals(""))&&(questionType == 0)&&questionPool == 0)
        {
            System.out.println("进行了题目关键字搜索而没进行题型检索");
            result = questionVMService.selectByQuestionName(page, questionName);
        }

        //进行了题型检索而没进行题目关键字检索
        else if((questionType != 0)&&(questionName.equals(""))&& questionPool == 0)
        {
            System.out.println("进行了题型检索而没进行题目关键字检索");
            result = questionVMService.selectByQuestionType(page, questionType);
        }

        //001
        else if(questionName.equals("")&& questionType==0 && questionPool != 0){
            System.out.println("进行了题目种类检索，没进行题型检索、关键字检索");
            result = questionVMService.selectByQuestionPool(page,questionPool);
        }

        else if (questionType == 0 && questionPool !=0 && !questionName.equals("")){
            System.out.println("进行了题目种类检索和关键字检索，没进行题型检索");
            result = questionVMService.selectByQuestionPoolAndName(page,questionPool,questionName);
        }

        //011
        else if(questionType != 0 && questionPool != 0 && questionName.equals("")){
            System.out.println("进行题型和题目种类检索，没进行关键字检索");
            result = questionVMService.selectByQuestionTypeAndQuestionPool(page,questionType,questionPool);
        }

        else if (!questionName.equals("") && questionPool == 0 && questionType != 0){
            System.out.println("进行关键字和题目类型检索，没进行题目种类检索");
            result = questionVMService.selectByQuestionTypeAndQuestionName(page,questionType,questionName);
        }

        //111检索
        else if(questionType != 0 && questionPool != 0 && !questionName.equals(""))
        {
            System.out.println("进行了全部检索");
            result = questionVMService.selectByAllConditionQuestionVM(page, questionName, questionType,questionPool);
        }

        //获取Content中的标题
        List<QuestionVM> records = result.getRecords();
        for(int i=0;i<records.size();i++)
        {
            QuestionObject questionObject = null;
            questionObject = JSON.parseObject(records.get(i).getContent(), QuestionObject.class);
            records.get(i).setContent(StrOperateUtil.removeTag(questionObject.getTitleContent()));
        }
        //查询所有的题型
        List<Type> list = typeService.list();
        //设置model、返回视图
        model.addAttribute("typeList",list);
        model.addAttribute("questionList",records);
        model.addAttribute("jumpUrl","/admin/questionsList?pn=");
        model.addAttribute("qType","&qType=");
        model.addAttribute("qTypeValue",questionType);
        model.addAttribute("qName","&name=");
        model.addAttribute("qPool","&qPool=");
        model.addAttribute("qPoolValue",questionPool);
        model.addAttribute("qNameValue",questionName);
        model.addAttribute("page",result);
        // 显示管理员已登录
        model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "admin/questions_list";
    }


    // 修改状态
    @Transactional(rollbackFor = Exception.class)
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


    // 批量删除
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("admin/deleteQuestionsCounts")
    public String deleteCount(@RequestParam String ids){//ids是复选框名字
        List<String> delList = new ArrayList<>();
        String[] strs = ids.split(",");
        for (String str : strs) {
            delList.add(str);
        }
        boolean b = questionService.removeByIds(delList);
        boolean b1 = contentService.removeByIds(delList);
        return "redirect:/admin/questionsList";
    }

    //删除
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("admin/deleteQuestion/{id}")
    public String deleteQuestion(@PathVariable Integer id) {
        questionService.removeById(id);
        contentService.removeById(id);
        return "redirect:/admin/questionsList";
    }

    //更新
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/admin/updateQuestion")
    public String updateSingleQuestion(HttpServletRequest request){
        Integer id = Integer.parseInt( request.getParameterValues("id")[0]);
        QuestionEditVM questionEditVM = new QuestionEditVM();
        questionEditVM.setId(id);
        int questionType = Integer.parseInt(request.getParameter("questionType"));
        questionEditVM.setQuestionType(questionType);
        Integer questionPool = Integer.parseInt(request.getParameterValues("questionPoolValue")[0]);
        questionEditVM.setQuestionPool(questionPool);
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

        if (request.getParameterValues("correct") == null){
            int i = questionEditVMService.updateQuestionExcepConrrect(questionEditVM);
        }else{
            if(questionType == StaticVariableUtil.singleSelectType){
                String corrects = request.getParameterValues("correct")[0];
                questionEditVM.setCorrect(corrects);
            }else if(questionType == StaticVariableUtil.moreSelectType){
                String[] corrects = request.getParameterValues("correct");
                questionEditVM.setCorrect(StringUtils.join(corrects));
            }
            else if (questionType == StaticVariableUtil.JudgmentalType){
                questionEditVM.setCorrect(request.getParameterValues("correct")[0]);
            }
            int i = questionEditVMService.updateQuestion(questionEditVM);
        }
        return "redirect:/admin/questionsList";
    }


    //进入更新页面
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/admin/updateQuestionPage/{id}")
    public String toUpdateQuestionPage(@PathVariable("id") Integer id, Model model)
    {
        QuestionEditVM questionEditVM = questionEditVMService.selectByConditionQuestionVM(id);

        List<Type> typeList = typeService.list();
        model.addAttribute("typeList",typeList);
        //获取题目、选项相关内容
        String content = questionEditVM.getContent();
        QuestionObject questionObject = JSON.parseObject(content, QuestionObject.class);
        if (questionEditVM.getQuestionType()==1)
        {
            model.addAttribute("questionEditVM",questionEditVM);
            int size = questionObject.getQuestionItemObjects().size();
            model.addAttribute("size",size);
            model.addAttribute("questionObject",questionObject);
            // 显示管理员已登录
            model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
            return "admin/update_singleChoice";
        }
        else if(questionEditVM.getQuestionType()==2){
            model.addAttribute("questionEditVM",questionEditVM);
            int size = questionObject.getQuestionItemObjects().size();
            model.addAttribute("size",size);
            model.addAttribute("questionObject",questionObject);
            // 显示管理员已登录
            model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
            return "admin/update_moreChoice";
        }
        else if(questionEditVM.getQuestionType()==3){
            model.addAttribute("questionEditVM",questionEditVM);
            int size = questionObject.getQuestionItemObjects().size();
            List<QuestionItemObject> questionItemObjects =
                    questionObject.getQuestionItemObjects();
            model.addAttribute("questionObject",questionObject);
            // 显示管理员已登录
            model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
            return "admin/update_judgemental";
        }
        else if(questionEditVM.getQuestionType()==4){
            return "redirect:/admin/questionsList";
        }
        else{
            return "redirect:/admin/questionsList";
        }
    }

}
