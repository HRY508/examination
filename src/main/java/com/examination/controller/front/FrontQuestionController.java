package com.examination.controller.front;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.FrontQuestionVM;
import com.examination.bean.QuestionObject;
import com.examination.service.FrontQuestionVMService;
import com.examination.utils.GlobalUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/11 19:45
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class FrontQuestionController {

    @Autowired
    private FrontQuestionVMService frontQuestionVMService;

    @RequestMapping("/questionList")
    public String getQuestionList(HttpServletRequest request,
                                  @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                  @RequestParam(required = false, defaultValue = "", value = "searchName") String searchName,
                                  @RequestParam(required = false, defaultValue = "", value = "searchId") Integer searchId,
                                  Integer userId){
        userId = GlobalUserUtil.getUser().getId();
        if(userId == null){
            return "error/4xx";
        }
        Page page=new Page(pn,15);
        Page<FrontQuestionVM> questionVMPage=null;
        if("".equals(searchName) && searchId == null){
            System.out.println("无条件全部查询");
            questionVMPage=frontQuestionVMService.getList(page,userId);
        }else if(!"".equals(searchName) && searchId == null){
            System.out.println("搜索题干，无id查询");
            questionVMPage=frontQuestionVMService.getListByName(page,userId,searchName);
        }else if("".equals(searchName) && searchId != null){
            System.out.println("无题干，搜索id查询");
            questionVMPage=frontQuestionVMService.getListById(page,userId,searchId);
        }else if(!"".equals(searchName) && searchId != null){
            System.out.println("搜索id，题干查询");
            questionVMPage=frontQuestionVMService.getListByList(page,searchId,searchName,userId);
        }
        List<FrontQuestionVM> records = questionVMPage.getRecords();
        for(int i=0;i<records.size();i++)
        {
            QuestionObject questionObject = null;
            questionObject = JSON.parseObject(records.get(i).getContent(), QuestionObject.class);
            records.get(i).setContent(questionObject.getTitleContent());
        }

        request.setAttribute("questionList",records);
        request.setAttribute("page",questionVMPage);
        request.setAttribute("jumpUrl","/user/questionList?pn=");
        request.setAttribute("searchNameValue","");
        request.setAttribute("qId","");
        request.setAttribute("qName","");
        request.setAttribute("searchIdValue","");
        if(searchId == null && !"".equals(searchName)){
            request.setAttribute("qName","&searchName=");
            request.setAttribute("searchNameValue",searchName);
        }
        if ("".equals(searchName) && searchId != null){
            request.setAttribute("searchIdValue",searchId);
            request.setAttribute("qId","&searchId=");
        }
        return "user/problem";
    }
}
