package com.examination.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.User;

import java.util.List;


public interface UserService extends IService<User> {

    // 添加用户
    public int addUser(User user);

    List<User> getList();

    boolean equalsByPwd(String oldPwd, Integer userId, String userName);

    boolean updatePwdByUserId(String sysMd5, Integer userId);

    boolean selectByStatus(String username);


}
