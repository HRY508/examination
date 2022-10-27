package com.examination.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.examination.bean.User;
import com.examination.service.UserService;
import com.examination.utils.ShiroMd5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author he
 * @Data 2022/9/30 22:09
 */
@Slf4j
@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @RequestMapping("/registration")
    public String toRegisterPage(Model model){
        User user = new User();
        model.addAttribute("reg",user);
        return "registration";
    }

    @RequestMapping("/register")
    public String register(@RequestParam("realName")String realName,
                           @RequestParam("uId")String uId,
                           @RequestParam("profession")String profession,
                           @RequestParam("sex")Integer sex,
                           @RequestParam("username")String userName,
                           @RequestParam("password")String password,
                           @RequestParam("repassword")String repassword,
                           Model model){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        User user = new User();
        user.setRealName(realName);
        user.setUId(uId);
        user.setProfession(profession);
        user.setSex(sex);
        user.setUserName(userName);
        user.setSex(1);//新注册的用户启用状态
        if (userService.getOne(wrapper)!=null){
            model.addAttribute("regMsg","该用户名已存在");
            model.addAttribute("reg",user);
            return "registration";
        }

        else if(!repassword.equals(password)){
            model.addAttribute("regMsg","两次输入密码不一致");
            return "registration";
        }

        else{
            user.setPassword(ShiroMd5Util.SysMd5(userName,password));
            user.setPerms("user");
            boolean save = userService.save(user);
            System.out.println("注册成功"+user);
            if (save){
                model.addAttribute("regMsg","success");
                model.addAttribute("username",userName);
                return "login";
            }
            else{
                model.addAttribute("regMsg","未知错误，请联系管理员！");
                return "registration";
            }
        }

    }
}
