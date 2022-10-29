package com.examination.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.viewmodel.FrontQuestionVM;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/11 19:45
 */
public interface FrontQuestionVMService extends IService<FrontQuestionVM> {


    Page<FrontQuestionVM> getList(Page page,Integer userId);

    Page<FrontQuestionVM> getListByName(Page page, Integer userId, String searchName);

    Page<FrontQuestionVM> getListById(Page page, Integer userId,  Integer searchId);

    Page<FrontQuestionVM> getListByList(Page page, Integer searchId, String searchName,Integer userId);

    Page<FrontQuestionVM> selectFrontQuestionVMByPool(Page page, Integer userId,  Integer searchPool);
}
