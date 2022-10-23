package com.examination.controller.front;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.examination.bean.Type;
import com.examination.service.TypeService;
import com.examination.service.UserService;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.ShiroMd5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/10 22:09
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class PagesController {

    @Resource
    UserService userService;

    @RequiresAuthentication
    @RequestMapping("/logout")
    public String logout() {
        //在这里执行退出系统前需要清空的数据
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return "login";
    }

    @ResponseBody
    @PostMapping("/updatePwd")
    public Object updatePwd(@RequestBody String req) {
        Integer userId = GlobalUserUtil.getUser().getId();
        String userName = GlobalUserUtil.getUser().getUserName();
        String oldPwd = (String) JSONObject.parseObject(req).get("oldPwd");
        String newPwd = (String) JSONObject.parseObject(req).get("newPwd");
        String newPwd2 = (String) JSONObject.parseObject(req).get("newPwd2");

        HashMap<Object, Object> map = new HashMap<>();
        if (!newPwd.equals(newPwd2)) {
            map.put("status", 400);
            map.put("info", "新密码两次输入不一致");
            return map;
        }
        if (newPwd.equals(oldPwd)){
            map.put("status", 400);
            map.put("info", "新密码和老密码输入一致");
            return map;
        }
        if (newPwd.length() < 6){
            map.put("status", 400);
            map.put("info", "密码长度至少6位");
            return map;
        }
        if (userService.equalsByPwd(oldPwd, userId, userName)) {
            if (userService.updatePwdByUserId(ShiroMd5Util.SysMd5(userName, newPwd), userId)) {
                map.put("status", 200);
                map.put("info", "修改密码成功，请重新登录");
                return map;
            }
        }
        map.put("status", 500);
        map.put("info", "修改密码失败，请稍候重试！");
        return map;
    }
}
