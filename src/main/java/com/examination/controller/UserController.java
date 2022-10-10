package com.examination.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.User;
import com.examination.service.UserService;
import com.examination.utils.ShiroMd5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
                       @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                       @RequestParam(required = false , defaultValue = "" , value = "searchName") String searchName,
                       @RequestParam(required = false,defaultValue = "", value = "perms")String perms
                       ) {
        Page userPage = new Page(pn,2);
        Page<User> result =null;
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        if(searchName.equals("")&&perms.equals("")){
            result = userService.page(userPage,queryWrapper);
        }else if(!searchName.equals("")&&perms.equals("")){
            queryWrapper.like(User::getUserName,searchName).or().like(User::getRealName,searchName).or().like(User::getUId,searchName);
            result = userService.page(userPage,queryWrapper);
        }else if(searchName.equals("")&&!perms.equals("")){
            queryWrapper.eq(User::getPerms,perms);
            result = userService.page(userPage,queryWrapper);
        }else{
            queryWrapper.like(User::getUserName,searchName).or().like(User::getRealName,searchName).or().like(User::getUId,searchName).eq(User::getPerms,perms);
            result = userService.page(userPage, queryWrapper);
        }

        request.setAttribute("jumpUrl", "/admin/userList?pn=");
        request.setAttribute("qName", "&name=");
        request.setAttribute("qPerms", "&perms=");
        request.setAttribute("searchName", searchName);
        request.setAttribute("permsValue", perms);
        //此处得到的page对象,包含了current（当前页）,pages（总页数），total（总记录数），records（记录，就是查询到的List集合）
        request.setAttribute("page", result);
        return "admin/user_list";
    }

//    @RequestMapping("/admin/searchList")
//    public String searchSubmit(@RequestParam(value = "searchName",required = false,defaultValue = "")String searchName,
//                               @RequestParam(value = "")
//                               HttpServletRequest request,
//                               @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn
//                               ) {
////        IPage<User> page = userService.searchByPage(pn, 5, searchName);
//        Page<User> userPage=new Page<>(pn,2);
//        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        request.setAttribute("searchName",searchName);
//        queryWrapper.like("user_name",searchName).or().like("real_name",searchName).or().like("uId",searchName);
//        Page<User> result = userService.page(userPage, queryWrapper);
//        request.setAttribute("jumpUrl", "/admin/searchName?searchName="+searchName+"&pn=");
//        request.setAttribute("page", result);
//        return "admin/user_list";
//    }


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



    // 修改状态
    @ResponseBody
    @PostMapping("/admin/statusChange")
    public Object statusChange(@RequestBody String req){
        User u = new User();
        //转化为json数据
        JSONObject jsonObject = (JSONObject) JSONObject.parse(req);
        //获取点击前的按钮状态，该修改的id
        Integer id = Integer.parseInt(((String) jsonObject.get("id")));
        //转换为Integer
        Integer uStatus = (Integer) jsonObject.get("uStatus");
        uStatus = uStatus==0?1:0;
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("u_status",uStatus);
        boolean update = userService.update(wrapper);
        if (update){
            Map<String, Object> rep = new HashMap<String, Object>();
            rep.put("code", 200);
            rep.put("status",uStatus);//返回按钮状态，前端根据状态修改按钮的文本内容
            return rep;
        }
        else{
            return null;
        }
    }

    // 添加用户
    @PostMapping("/admin/addUser")
    public String addUser(@RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          @RequestParam("uId") String uId,
                          @RequestParam("realName") String realName,
                          @RequestParam("sex") Integer sex,
                          @RequestParam("profession") String profession,
                          @RequestParam("perms") String perms,
                            HttpServletRequest request){
        // 判断userName是否唯一
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,userName);
        User one = userService.getOne(lambdaQueryWrapper);
        if(one.getUserName().equals(userName)){
        //    request.setAttribute("message","添加失败,用户名已存在,请更换!"); 功能未实现，提交后，模态框关闭，无法返回message?
        }else{
            User user=new User();
            user.setUserName(userName);
            user.setPassword(ShiroMd5Util.SysMd5(userName,password));
            user.setUId(uId);
            user.setRealName(realName);
            user.setSex(sex);
            user.setProfession(profession);
            user.setPerms(perms);
            userService.save(user);
        }

        return "redirect:/admin/userList";
    }


    // 修改用户
    @RequestMapping(value = "/admin/updateUser", method = RequestMethod.POST)
    public String updateUser(@RequestParam("id") Integer id,
                             @RequestParam("userNameUpdate") String userName,
                             @RequestParam("passwordUpdate") String password,
                             @RequestParam("uIdUpdate") String uId,
                             @RequestParam("realNameUpdate") String realName,
                             @RequestParam("sexUpdate") Integer sex,
                             @RequestParam("professionUpdate") String profession,
                             @RequestParam("permsUpdate") String perms){
        if(id!=null){
            // 先查询一次
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getId,id);
            User user = userService.getOne(queryWrapper);
            if (!user.getPassword().equals(password)) {
                user.setPassword(ShiroMd5Util.SysMd5(userName,password));
            }
            if (!user.getUId().equals(uId)){
                user.setUId(uId);
            }
            if(!user.getRealName().equals(realName)){
                user.setRealName(realName);
            }
            if(user.getSex()!=sex){
                user.setSex(sex);
            }
            if(!user.getProfession().equals(password)){
                user.setProfession(profession);
            }
            if(!user.getPerms().equals(perms)){
                user.setPerms(perms);
            }
            userService.updateById(user);
        }
        return "redirect:/admin/userList";
    }



}
