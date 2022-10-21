package com.examination.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.examination.bean.Type;
import com.examination.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/10 22:09
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class PagesController {




    @RequestMapping("/questionDetail")
    public String toQuestionDetailPage(){
        return "user/question_detail";
    }

    @RequiresAuthentication
    @RequestMapping("/logout")
    public String logout(){
        //在这里执行退出系统前需要清空的数据
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            subject.logout();
        }
        return "login";
    }

}
