package com.examination.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.QuestionVM;


public interface QuestionVMService extends IService<QuestionVM> {

    Page<QuestionVM> getQuestionList(Page page);

    Page<QuestionVM> selectByQuestionType(Page page, Integer questionType);

    Page<QuestionVM> selectByQuestionName(Page page, String questionName);

    Page<QuestionVM> selectByConditionQuestionVM(Page page, Integer questionType, String questionName);
}
