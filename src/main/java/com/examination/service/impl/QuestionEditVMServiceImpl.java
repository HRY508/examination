package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.viewmodel.QuestionEditVM;
import com.examination.mapper.QuestionEditVMMapper;
import com.examination.service.QuestionEditVMService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author he
 * @Data 2022/10/7 16:06
 */
@Service
public class QuestionEditVMServiceImpl extends ServiceImpl<QuestionEditVMMapper, QuestionEditVM> implements QuestionEditVMService {
    @Override
    @Transactional(readOnly = true)
    public QuestionEditVM selectByConditionQuestionVM(Integer questionId) {
        QuestionEditVMMapper baseMapper = this.baseMapper;
        QuestionEditVM questionEditVM = baseMapper.selectByConditionQuestionVM(questionId);
        return questionEditVM;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateQuestion(QuestionEditVM questionEditVM) {
        QuestionEditVMMapper baseMapper = this.baseMapper;
        int res = baseMapper.updateQuestion(questionEditVM);
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateQuestionExcepConrrect(QuestionEditVM questionEditVM) {
        QuestionEditVMMapper baseMapper = this.baseMapper;
        int res = baseMapper.updateQuestionExcepCorrect(questionEditVM);
        return 0;
    }
}
