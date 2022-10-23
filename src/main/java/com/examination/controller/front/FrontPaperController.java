package com.examination.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.Paper;
import com.examination.service.PaperService;
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
 * @Date: 2022/10/14 9:52
 */
@RequestMapping("/user")
@Controller
@Slf4j
public class FrontPaperController{

    @Autowired
    private PaperService paperService;

    @RequestMapping("/paper")
    public String paperList(HttpServletRequest request,
                            @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                            Integer userId){
        userId = GlobalUserUtil.getUser().getId();
        if(userId == null){
            return "error/4xx";
        }
        Page page=new Page(pn,15);
        Page<Paper> paperPage = paperService.page(page);
        List<Paper> papers = paperPage.getRecords();
        request.setAttribute("paperList",papers);
        request.setAttribute("page",paperPage);
        request.setAttribute("pNum",1);

        // 显示用户已登录
        request.setAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "user/paper";
    }

}
