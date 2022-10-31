package com.examination.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.viewmodel.TypeVM;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/29 17:56
 */
public interface TypeVMService extends IService<TypeVM> {


    Page<TypeVM> selectByPage(Page<TypeVM> page);

    Page<TypeVM> pageBySearchName(Page<TypeVM> page, String searchName);


}
