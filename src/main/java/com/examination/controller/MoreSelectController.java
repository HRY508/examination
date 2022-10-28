package com.examination.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.examination.bean.Type;
import com.examination.service.QuestionService;
import com.examination.service.TypeService;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.StaticVariableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/6 20:37
 */
@Controller
@Slf4j
public class MoreSelectController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private TypeService typeService;

    @RequestMapping("/admin/moreEditors")
    public String toMoreChoicePage(Model model){
        //查询所有的题型
        QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.select("q_type","q_pool");
        List<Type> list = typeService.list(typeQueryWrapper);
        model.addAttribute("typeList",list);
        // 显示管理员已登录
        model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "admin/calendar";
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/admin/moreSelect")
    public String moreSelect(HttpServletRequest request,Model model) throws Exception {
        Integer questionType = StaticVariableUtil.moreSelectType;
        questionService.insertSelectQuestion(request, GlobalUserUtil.getUser().getUserName(),questionType);
        log.info("===================="+request.getParameter("difficult"));
        // 显示管理员已登录
        model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "admin/calendar";
    }
}
