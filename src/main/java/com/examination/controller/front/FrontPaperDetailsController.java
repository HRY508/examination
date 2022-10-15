package com.examination.controller.front;

import com.examination.service.PaperDetailsVMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.annotation.Resource;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/14 9:32
 */
@RequestMapping("/user/paper")
@Controller
@Slf4j
public class FrontPaperDetailsController {

    @Resource
    private PaperDetailsVMService paperDetailsVMService;

    @RequestMapping("/paperDetails")
    public String toPaperDetails(@RequestParam("pid")Integer pid,
                                 Model model){
        if(pid == null || pid == 0){
            return "error/4xx";
        }
        return "user/paper_details";
    }


}
