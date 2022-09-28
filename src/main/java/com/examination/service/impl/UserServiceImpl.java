package com.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.User;
import com.examination.mapper.UserMapper;
import com.examination.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/9/28 16:49
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
