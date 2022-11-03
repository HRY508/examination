package com.examination.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.examination.bean.User;
import com.examination.service.UserService;
import com.examination.utils.ShiroMd5Util;
import com.examination.utils.StaticVariableUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.Map;

/**
 * @Description
 * @Author he
 * @Data 2022/9/30 22:09
 */
@Slf4j
@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @RequestMapping("/registration")
    public String toRegisterPage(Model model){
        User user = new User();
        model.addAttribute("reg",user);
        return "registration";
    }

    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    @PostMapping("/register")
    public Object register(@RequestBody String req) {

        String realName = JSONObject.parseObject(req).get("realName").toString();
        String uId =JSONObject.parseObject(req).get("uId").toString();
        String profession =  JSONObject.parseObject(req).get("profession").toString();
        Integer sex = Integer.parseInt((String) JSONObject.parseObject(req).get("sex")) ;
        String userName = JSONObject.parseObject(req).get("username").toString();
        String password = JSONObject.parseObject(req).get("password").toString();
        String rePassword = JSONObject.parseObject(req).get("repassword").toString();

        Map<String, Object> map = new Hashtable<>();
        if (!StringUtils.isNumeric(uId)) {
            map.put("code", StaticVariableUtil.FAILCODE);
            map.put("msg", "学号格式不正确，请输入数字！");
            return map;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        if (userService.getOne(wrapper) != null) {
            map.put("code", StaticVariableUtil.FAILCODE);
            map.put("msg", "该用户名已存在，请更换！");
            return map;
        }
        User user = new User();
        user.setRealName(realName);
        user.setUId(uId);
        user.setProfession(profession);
        user.setSex(sex);
        user.setUserName(userName);
        user.setUStatus(1);//新注册的用户启用状态
        user.setPassword(ShiroMd5Util.SysMd5(userName, password));
        user.setPerms("user");

        if (userService.save(user)) {
            map.put("code", StaticVariableUtil.SUCCESSCODE);
            map.put("msg", "注册成功！");
            return map;
        } else {
            map.put("code", StaticVariableUtil.HALFSUCCESSCODE);
            map.put("msg", "数据库出了点问题，请稍后重试！");
            return map;
        }
    }
}
