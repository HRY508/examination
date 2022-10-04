package com.examination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.User;

import java.util.List;


public interface UserService extends IService<User> {

    // 添加用户
    public int addUser(User user);



}
