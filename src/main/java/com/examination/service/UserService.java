package com.examination.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.User;



public interface UserService extends IService<User> {

    // 添加用户
    public int addUser(User user);

}
