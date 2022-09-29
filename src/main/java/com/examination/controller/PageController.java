package com.examination.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author he
 * @Data 2022/9/27 7:41
 */
@Controller
@RequestMapping("/admin")
public class PageController {

    @RequestMapping({"","/index","/"})
    public String indexPage(){
        return "admin/index";
    }

    @GetMapping({"/{url}"})
    public String toPage(@PathVariable("url") String url){
        return "admin/"+url;
    }

}
