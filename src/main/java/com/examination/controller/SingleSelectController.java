package com.examination.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.Question;
import com.examination.bean.User;
import com.examination.service.QuestionService;
import com.examination.service.UserService;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.StaticVariableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @Description
 * @Author he
 * @Data 2022/10/3 23:52
 */
@Controller
public class SingleSelectController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/admin/singleEditors")
    public String toSingleChoicePage(){
        return "admin/editors";
    }


    @RequestMapping("/admin/singleSelect")
    public String singleSelect(HttpServletRequest request){
        Integer questionType = StaticVariableUtil.singleSelectType;
        questionService.insertSelectQuestion(request,GlobalUserUtil.getUser().getUserName(),questionType);
        return "admin/editors";
    }
}
