package com.examination.service.impl;

import com.examination.bean.User;
import com.examination.mapper.UserMapper;
import com.examination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author he
 * @Data 2022/9/27 19:56
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public List<User> queryUserList() {
        List<User> users = userMapper.queryUserList();
        return users;
    }

    @Override
    public User queryUserByName(String username) {
        return  userMapper.queryUserByName(username);
    }


    @Override
    public int addUser(User user) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(Integer id) {
        return 0;
    }
}
