package com.examination.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.Type;

public interface TypeService extends IService<Type> {
    Page<Type> pageBySearchName(Page<Type> page, String searchName);

    Integer selectPoolByIdAndDelete(int id);
}
