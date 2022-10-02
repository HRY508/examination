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

//    @GetMapping({"/admin/{url}"})
//    public String toPage(@PathVariable("url") String url){
//        return "admin/"+url;
//    }

//    @RequestMapping("/registration")
//    public String toRegister(){
//        return "registration";
//    }
}
