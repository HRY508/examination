package com.examination.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.Paper;
import com.examination.viewmodel.RankVM;
import com.examination.bean.User;
import com.examination.service.*;
import com.examination.utils.GlobalUserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author he
 * @Data 2022/9/29 20:38
 */
@Controller
public class LoginController {


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private PaperService paperService;
    @Resource
    private RankVMService rankVMService;
    @Resource
    private QuestionDetailsService questionDetailsService;
    @Resource
    private UserService userService;
    @Resource
    private SessionDAO sessionDAO;

    @RequestMapping({"/login","","/"})
    public String toLoginPage(Model model){
        model.addAttribute("check",false);
        return "login";
    }


    @RequestMapping("/user/index")
    public String toUserPage(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                             Model model){
        // 查考试
        List<Paper> list = paperService.list(null);
        model.addAttribute("paper",list);

        // 查排名
        // 默认显示第1页，显示20个数据
        Page page = new Page(pn,20);
        Page<RankVM> rankVMList = rankVMService.getList(page);
        model.addAttribute("userList",rankVMList.getRecords());
        model.addAttribute("page",rankVMList);

        // 查有多少人今日完成了
        Integer finishNum = questionDetailsService.getFinishNum();
        Integer rightNum = questionDetailsService.getrightNum();
        model.addAttribute("finishNum",finishNum);
        model.addAttribute("rightNum",rightNum);

        // 显示用户已登录
        model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "user/index";
    }


    @PostMapping("/loginInto")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpServletRequest request){
        if (username.equals("") || password.equals("")){
            model.addAttribute("msg","请输入账号密码");
            return "login";
        }
        if (!userService.selectByStatus(username)){
            model.addAttribute("msg","用户已被禁用，请与管理员联系！");
            return "login";
        }
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
        // 防止重复登陆
//        Collection<Session> sessions = sessionDAO.getActiveSessions();
//        if (!subject.isAuthenticated()){
//            for (Session session : sessions) {
//                subject.logout();
//                model.addAttribute("msg","用户已登录");
//                return "login";
//            }
//        }

        User user = (User)subject.getPrincipal();
        if(user.getPerms().equals("user")){
            logger.info("用户"+user.getUserName()+"登录了的系统");
            return "redirect:/user/index";
        }
        else if(user.getPerms().equals("admin")){
            logger.info("管理员"+user.getUserName()+"登录了的系统");
            return "redirect:/admin/index";
        }
        else{
            return "redirect:/login";
        }
    }
}
