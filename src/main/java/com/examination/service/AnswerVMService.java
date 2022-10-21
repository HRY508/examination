package com.examination.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.AnswerVM;
import com.examination.bean.QuestionVM;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/20 20:19
 */
public interface AnswerVMService extends IService<AnswerVM> {

     Page<AnswerVM> selectList(Page page);

    Page<AnswerVM> selectListByPId(Page page, Integer pId);

    Page<AnswerVM> selectListByPIdAndUserName(Page page, Integer pId, String searchName);

    Page<AnswerVM> selectListByUserName(Page page, String searchName);
}


