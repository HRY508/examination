package com.examination.controller;

import com.examination.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author he
 * @Data 2022/9/29 20:38
 */
@Controller
public class LoginController {

    @RequestMapping({"/login","","/"})
    public String toLoginPage(){
        return "login";
    }
    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "admin/500";
    }

    @RequestMapping("/user/index")
    public String toUserPage(){
        return "user/index";
    }

    @PostMapping("/loginInto")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            subject.login(token);
        }catch(UnknownAccountException e){
            model.addAttribute("msg","用户不存在");
            return "/login";
        }
        catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "/login";
        }
        User user = (User)subject.getPrincipal();
        if(user.getPerms().equals("user")){
            return "redirect:/user/index";
        }
        else if(user.getPerms().equals("admin")){
            return "redirect:/admin/index";
        }
        else{
            return "redirect:/login";
        }
    }
}
