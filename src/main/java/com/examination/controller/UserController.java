package com.examination.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.User;
import com.examination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                               @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn
                               ) {
//        IPage<User> page = userService.searchByPage(pn, 5, searchName);
        Page<User> userPage=new Page<>(pn,2);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        request.setAttribute("searchName",searchName);
        queryWrapper.like("user_name",searchName).or().like("real_name",searchName);
        Page<User> result = userService.page(userPage, queryWrapper);
        request.setAttribute("jumpUrl", "/admin/searchName?searchName="+searchName+"&pn=");
        request.setAttribute("page", result);
        return "admin/user_list";
    }


    // 批量删除
    @RequestMapping("admin/deleteCounts")
    public String deleteCount(@RequestParam String ids){//ids是复选框名字
        List<String> delList = new ArrayList<>();
        String[] strs = ids.split(",");
        for (String str : strs) {
            delList.add(str);
        }
        userService.removeByIds(delList);
        return "redirect:/admin/userList";
    }
    //删除
    @GetMapping("admin/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.removeById(id);
        return "redirect:/admin/userList";
   }


//    @PostMapping("/admin/statusChange")
//    public String statusChange(){
//

    @ResponseBody
    @PostMapping("/admin/statusChange")
    public Object statusChange(@RequestBody String req){
        User u = new User();
        //转化为json数据
        JSONObject jsonObject = (JSONObject) JSONObject.parse(req);
        //获取点击前的按钮状态，该修改的id
        Integer id = Integer.parseInt(((String) jsonObject.get("id")));
        System.out.println("id"+id);
        //转换为Integer
        Integer uStatus = (Integer) jsonObject.get("uStatus");
        uStatus = uStatus==0?1:0;
        System.out.println("最终修改为状态："+uStatus);
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("u_status",uStatus);
        boolean update = userService.update(wrapper);
        if (update){
            Map<String, Object> rep = new HashMap<String, Object>();
            rep.put("code", 200);
            rep.put("msg", "成功");
            System.out.println("成功");
            return rep;
        }
        else{
            return null;
        }
    }




}
