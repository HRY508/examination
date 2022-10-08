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

//    @GetMapping({"/admin/{url}"})
//    public String toPage(@PathVariable("url") String url){
//        return "admin/"+url;
//    }

//    @RequestMapping("/registration")
//    public String toRegister(){
//        return "registration";
//    }
}
