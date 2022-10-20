package com.examination.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.AnswerVM;
import com.examination.bean.Paper;
import com.examination.service.AnswerVMService;
import com.examination.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/20 18:25
 */
@Controller
@Slf4j
@RequestMapping("/admin")
public class AnswerController {

    @Autowired
    private AnswerVMService answerVMService;
    @Autowired
    private PaperService paperService;

    @RequestMapping("/answerList")
    public String toAnswer(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                           @RequestParam(required = false, defaultValue = "0", value = "pid") Integer pId,
                           Model model){
        //默认显示第1页，显示5个数据
        Page page = new Page(pn,1);
        Page<AnswerVM> result = null;
        if (pId == 0 || pId == null){
           result = answerVMService.selectList(page);
           pId = 0;
        }else if (pId != 0 && pId != null){
            result = answerVMService.selectListByPId(page,pId);
        }
        // 查询卷子
        LambdaQueryWrapper<Paper> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Paper::getPId,Paper::getPName);
        List<Paper> list = paperService.list(queryWrapper);

        model.addAttribute("page",result);
        model.addAttribute("jumpUrl","/admin/answerList?pn=");
        model.addAttribute("paperId",pId);
        model.addAttribute("paperList",list);
        model.addAttribute("qid","&pid=");
        return "admin/paper_answer";
    }

}
