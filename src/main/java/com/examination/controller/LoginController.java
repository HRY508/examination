package com.examination.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.examination.bean.Paper;
import com.examination.bean.User;
import com.examination.service.PaperService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description
 * @Author he
 * @Data 2022/9/29 20:38
 */
@Controller
public class LoginController {

    @Autowired
    private PaperService paperService;

    @RequestMapping({"/login","","/"})
    public String toLoginPage(Model model){
        model.addAttribute("check",false);
        return "login";
    }


    @RequestMapping("/user/index")
    public String toUserPage(Model model){
        List<Paper> list = paperService.list(null);
        model.addAttribute("paper",list);
        return "user/index";
    }

    @PostMapping("/loginInto")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //获取记住我，的选中状态，如果未选中，获取的是null，如果选中获取的是value值，没有设置value值选中时获取的是on
        String rememberMe = request.getParameter("rememberMe");
        if(rememberMe!=null){
          token.setRememberMe(true);
        }
        try{
            subject.login(token);
        }catch(UnknownAccountException e){
            model.addAttribute("msg","用户不存在");
            return "login";
        }
        catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
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
