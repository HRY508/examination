package com.examination.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.User;
import com.examination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author he
 * @Data 2022/9/28 19:14
 */
@Controller
public class UserController {

    @Autowired
    private  UserService userService;

    @RequestMapping("/admin/userList")
    //pn是每次传回来的当前页
    public Object view(HttpServletRequest request,
                       @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn) {
//        IPage<User> page = userService.selectByAllPage(pn, 5);
        Page<User> userPage=new Page<>(pn,2);
        Page<User> result=userService.page(userPage);
        request.setAttribute("jumpUrl", "/admin/userList?pn=");
        //此处得到的page对象,包含了current（当前页）,pages（总页数），total（总记录数），records（记录，就是查询到的List集合）
        request.setAttribute("page", result);
        return "admin/user_list";
    }
    @RequestMapping("/admin/searchName")
    public String searchSubmit(@RequestParam(value = "searchName",required = false,defaultValue = "")String searchName,
                               HttpServletRequest request,
                               @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn) {
//        IPage<User> page = userService.searchByPage(pn, 5, searchName);
        Page<User> userPage=new Page<>(pn,2);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("userName",searchName).or().like("realName",searchName);
        Page<User> result = userService.page(userPage, queryWrapper);
        request.setAttribute("jumpUrl", "/admin/searchName?searchName="+searchName+"&pn=");
        request.setAttribute("page", result);
        return "admin/user_list";
    }





}
