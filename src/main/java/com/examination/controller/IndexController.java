package com.examination.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.Paper;
import com.examination.bean.Question;
import com.examination.bean.QuestionDetails;
import com.examination.bean.User;
import com.examination.service.PaperService;
import com.examination.service.QuestionDetailsService;
import com.examination.service.QuestionService;
import com.examination.service.UserService;
import com.examination.utils.GlobalUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description
 * @Author he
 * @Data 2022/10/30 9:57
 */

@Controller
public class IndexController {
    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionDetailsService questionDetailsService;

    @Autowired
    PaperService paperService;

    @Autowired
    UserService userService;

    @RequestMapping({"/admin/","/admin/index","/admin"})
    public String indexPage(Model model){
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        BaseMapper<Question> mapper = questionService.getBaseMapper();
        Integer questionTotal = mapper.selectCount(wrapper);
        QueryWrapper<QuestionDetails> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("result",1);
        BaseMapper<QuestionDetails> baseMapper = questionDetailsService.getBaseMapper();
        Integer finishedTotal = baseMapper.selectCount(wrapper1);
        QueryWrapper<Paper> wrapper3 = new QueryWrapper<>();
        wrapper3.select("p_id");
        BaseMapper<Paper> mapper3 = paperService.getBaseMapper();
        Integer paperCount = mapper3.selectCount(wrapper3);
        QueryWrapper<User> wrapper4 = new QueryWrapper<>();
        wrapper4.select("id").eq("perms","user");
        BaseMapper<User> mapper4 = userService.getBaseMapper();
        Integer userCount = mapper4.selectCount(wrapper4);

        QueryWrapper<Question> wrapper5 = new QueryWrapper<>();
        wrapper5.select("question_type").eq("question_type",1);
        BaseMapper<Question> mapper5 = questionService.getBaseMapper();
        Integer singleCount = mapper5.selectCount(wrapper5);

        QueryWrapper<Question> wrapper6 = new QueryWrapper<>();
        wrapper6.select("question_type").eq("question_type",2);
        BaseMapper<Question> mapper6 = questionService.getBaseMapper();
        Integer moreCount = mapper6.selectCount(wrapper6);

        QueryWrapper<Question> wrapper7 = new QueryWrapper<>();
        wrapper7.select("question_type").eq("question_type",3);
        BaseMapper<Question> mapper7 = questionService.getBaseMapper();
        Integer judgeCount = mapper7.selectCount(wrapper7);

        QueryWrapper<Question> wrapper8 = new QueryWrapper<>();
        wrapper8.select("status").eq("status",1);
        BaseMapper<Question> mapper8 = questionService.getBaseMapper();
        Integer statusCount = mapper8.selectCount(wrapper8);

        model.addAttribute("paperCount",paperCount);
        model.addAttribute("userCount",userCount);
        model.addAttribute("questionTotal",questionTotal);
        model.addAttribute("finishedTotal",finishedTotal);

        double singleRate = (((double)singleCount)/questionTotal);
        double moreRate = (((double)moreCount)/questionTotal);
        double judgeRate = (((double)judgeCount)/questionTotal);
        double statusRate = (((double)statusCount)/questionTotal);

        model.addAttribute("singleRate",Math.round(singleRate*100));
        model.addAttribute("moreRate",Math.round(moreRate*100));
        model.addAttribute("judgeRate",Math.round(judgeRate*100));
        model.addAttribute("statusRate",Math.round(statusRate*100));
        // 显示管理员已登录
        model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "admin/index";
    }
}
