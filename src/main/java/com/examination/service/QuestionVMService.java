package com.examination.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.viewmodel.QuestionVM;


public interface QuestionVMService extends IService<QuestionVM> {

    Page<QuestionVM> getQuestionList(Page page);

    Page<QuestionVM> selectByQuestionType(Page page, Integer questionType);

    Page<QuestionVM> selectByQuestionName(Page page, String questionName);

    Page<QuestionVM> selectByConditionQuestionVM(Page page, Integer questionType, String questionName);

    Page<QuestionVM> selectByQuestionTypeAndQuestionPool(Page page, Integer questionType, Integer questionPool);

    Page<QuestionVM> selectByQuestionPool(Page page, Integer questionPool);

    Page<QuestionVM> selectByAllConditionQuestionVM(Page page, String questionName, Integer questionType, Integer questionPool);
}
