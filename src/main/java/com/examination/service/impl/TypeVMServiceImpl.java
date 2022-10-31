package com.examination.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.viewmodel.TypeVM;
import com.examination.mapper.TypeVMMapper;
import com.examination.service.TypeVMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/29 17:57
 */
@Service
public class TypeVMServiceImpl extends ServiceImpl<TypeVMMapper, TypeVM> implements TypeVMService {
    @Autowired
    TypeVMMapper typeVMMapper;

    @Override
    public Page<TypeVM> selectByPage(Page<TypeVM> page) {
       return typeVMMapper.queryByPage(page);
    }

    @Override
    public Page<TypeVM> pageBySearchName(Page<TypeVM> page, String searchName) {
        return typeVMMapper.queryByPageAndSearchName(page,searchName);
    }


}
