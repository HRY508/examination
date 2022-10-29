package com.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.viewmodel.QuestionEditVM;

/**
 * @Description
 * @Author he
 * @Data 2022/10/7 15:59
 */

public interface QuestionEditVMService  extends IService<QuestionEditVM> {
    QuestionEditVM selectByConditionQuestionVM(Integer questionId);

    int updateQuestion(QuestionEditVM questionEditVM);
}
