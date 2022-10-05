package com.examination;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.User;
import com.examination.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class ExaminationApplicationTests {
    @Autowired
    UserService userService;
    @Test
    void contextLoads() {
        BaseMapper<User> mapper = userService.getBaseMapper();
        HashMap<String,Object> map = new HashMap<>();
        List<User> users = mapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testPage(){
        BaseMapper<User> userMapper = userService.getBaseMapper();
        //参数一 当前页、参数二、页面大小       第一页显示5条数据
        Page<User> page = new Page<>(1,5);
        userMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);
    }
    @Test
    void testUpdate(){
        User user = new User();
        user.setUserName("测试插入自动更新时间");
        boolean b = userService.save(user);
        System.out.println("添加："+b);
    }
    @Test
    void testGlobalUser(){

    }

}
