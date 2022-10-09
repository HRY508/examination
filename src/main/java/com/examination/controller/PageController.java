package com.examination.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author he
 * @Data 2022/9/27 7:41
 */
@Controller
public class PageController {

    @RequestMapping({"/admin/","/admin/index","/admin"})
    public String indexPage(){
        return "admin/index";
    }

    @RequestMapping("/admin/singleEditors")
    public String toSingleChoicePage(){
        return "admin/editors";
    }

    @RequestMapping("/admin/moreEditors")
    public String toMoreChoicePage(){
        return "admin/calendar";
    }

    @RequestMapping("/admin/judgmentalEditors")
    public String tojudgmentalPage(){
        return "admin/tree_view";
    }

    @RequestMapping("/admin/completionEditors")
    public String tocompletionPage(){
        return "admin/nestable";
    }

    //进入更新页面
    @GetMapping("/admin/updateQuestionPage/{id}")
    public String toUpdateQuestionPage(@PathVariable("id") Integer id, Model model)
    {
        System.out.println("进入了更新方法");
        QuestionEditVM questionEditVM = questionEditVMService.selectByConditionQuestionVM(id);
        //获取题目、选项相关内容
        String content = questionEditVM.getContent();
        QuestionObject questionObject = JSON.parseObject(content, QuestionObject.class);
        if (questionEditVM.getQuestionType()==1)
        {
            model.addAttribute("questionEditVM",questionEditVM);
            int size = questionObject.getQuestionItemObjects().size();
            model.addAttribute("size",size);
            model.addAttribute("questionObject",questionObject);
            return "admin/update_singleChoice";
        }
        else if(questionEditVM.getQuestionType()==2){
            model.addAttribute("questionEditVM",questionEditVM);
            int size = questionObject.getQuestionItemObjects().size();
            model.addAttribute("size",size);
            model.addAttribute("questionObject",questionObject);
            return "admin/update_moreChoice";
        }
        else if(questionEditVM.getQuestionType()==3){
            model.addAttribute("questionEditVM",questionEditVM);
            int size = questionObject.getQuestionItemObjects().size();
            List<QuestionItemObject> questionItemObjects =
                    questionObject.getQuestionItemObjects();
            model.addAttribute("questionObject",questionObject);
            return "admin/update_judgemental";
        }
        else if(questionEditVM.getQuestionType()==4){
            return "redirect:/admin/questionsList";
        }
        else{
            return "redirect:/admin/questionsList";
        }
    }

//    @GetMapping({"/admin/{url}"})
//    public String toPage(@PathVariable("url") String url){
//        return "admin/"+url;
//    }

//    @RequestMapping("/registration")
//    public String toRegister(){
//        return "registration";
//    }
}
