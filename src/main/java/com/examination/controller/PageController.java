package com.examination.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author he
 * @Data 2022/9/27 7:41
 */
@Controller
@RequestMapping("/admin")
public class PageController {


    @RequestMapping("/login")
    public String loginPage(){
        return "admin/login";
    }


    @GetMapping({"/{url}"})
    public String toPage(@PathVariable("url") String url){
        return "admin/"+url;
    }

    @RequestMapping({"/",""})
    public String dashobard(){
        return "admin/index";
    }

    @RequestMapping("/index")
    public String indexPage(){
        return "admin/index";
    }

    @RequestMapping("/common")
    public String leftnav(){
        return "admin/commons/common";
    }


    @PostMapping("/loginInto")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("email")String email, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            subject.login(token);
        }catch(UnknownAccountException e){
            e.printStackTrace();
            model.addAttribute("msg","用户不存在");
            return "admin/login";
        }
        catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "admin/login";
        }
        System.out.println("success");
        return "redirect:/admin/index";
    }
}
