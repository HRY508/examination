package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.Question;
import com.examination.bean.User;
import com.examination.mapper.UserMapper;
import com.examination.service.UserService;
import com.examination.utils.ShiroMd5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/9/28 16:49
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addUser(User user) {
        int result = userMapper.insert(user);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getList() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getId,User::getUserName,User::getRealName,User::getProfession);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean equalsByPwd(String oldPwd, Integer userId, String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        User user = userMapper.selectOne(queryWrapper);
        if (user.getPassword().equals(ShiroMd5Util.SysMd5(userName,oldPwd))){
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePwdByUserId(String sysMd5, Integer userId) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getPassword,sysMd5).eq(User::getId,userId);
        int update = userMapper.update(null, updateWrapper);
        if (update != 0){
            return true;
        }
       return false;
    }

    @Override
    public boolean selectByStatus(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getUStatus).eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        if (user.getUStatus()==1){
            return true;
        }
        return false;
    }


}
