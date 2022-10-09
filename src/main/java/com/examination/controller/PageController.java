package com.examination.controller;
import com.alibaba.fastjson.JSON;
import com.examination.bean.QuestionEditVM;
import com.examination.bean.QuestionItemObject;
import com.examination.bean.QuestionObject;
import com.examination.service.QuestionEditVMService;
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


}
