package com.examination.serice.impl;

import com.examination.bean.User;
import com.examination.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    UserMapper userMapper;
    @Test
    void queryUserList(){
        List<User> userList = userMapper.queryUserList();
        System.out.println(userList);
    }
    @Test
    void queryUserById(){
        User user = userMapper.queryUserByName("admin");
        System.out.println(user);
    }
}