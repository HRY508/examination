package com.examination.controller;
import com.alibaba.fastjson.JSON;
import com.examination.bean.QuestionEditVM;
import com.examination.bean.QuestionItemObject;
import com.examination.bean.QuestionObject;
import com.examination.bean.User;
import com.examination.service.QuestionEditVMService;
import com.examination.utils.GlobalUserUtil;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 管理员退出登录
     * @return
     */
    @RequiresAuthentication
    @RequestMapping("/admin/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
        }
        return "login";
    }

    /**
     * 修改密码
     */
//    public String updatePassword(){
//        User user = GlobalUserUtil.getUser();
//    }

}
