package com.examination.controller;

import com.examination.utils.LogReaderUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * @Description
 * @Author he
 * @Data 2022/10/01 22:54
 */

@Controller
public class LoggerController {

    @RequestMapping("/admin/logPage")
    public String toLogPage(Model model){
        try {
            ArrayList<String> names = LogReaderUtil.readFileNames();
            model.addAttribute("pathNames",names);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "admin/log";
    }

    @RequestMapping("/admin/logs/{name}")
    public String downLog(@PathVariable("name") String name,HttpServletResponse response) {
        System.out.println(name);
        String download = LogReaderUtil.download(response, "logs/" + name);
        return null;
    }


}
