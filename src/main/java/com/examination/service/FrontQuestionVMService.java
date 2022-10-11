package com.examination.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.FrontQuestionVM;
import com.examination.bean.Question;
import com.examination.bean.QuestionVM;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/11 19:45
 */
public interface FrontQuestionVMService extends IService<FrontQuestionVM> {


    Page<FrontQuestionVM> getList(Page page,Integer userId);

    Page<FrontQuestionVM> getListByName(Page page, String searchName,Integer userId);

    Page<FrontQuestionVM> getListById(Page page, Integer searchId,Integer userId);

    Page<FrontQuestionVM> getListByList(Page page, Integer searchId, String searchName,Integer userId);
}
