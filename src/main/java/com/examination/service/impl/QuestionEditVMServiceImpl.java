package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.QuestionEditVM;
import com.examination.bean.QuestionVM;
import com.examination.mapper.QuestionEditVMMapper;
import com.examination.mapper.QuestionVMMapper;
import com.examination.service.QuestionEditVMService;
import com.examination.service.QuestionVMService;
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
        QueryWrapper<QuestionEditVM> wrapper = new QueryWrapper<>();
        QuestionEditVMMapper baseMapper = this.baseMapper;
        QuestionEditVM questionEditVM = baseMapper.selectByConditionQuestionVM(questionId);
        return questionEditVM;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateQuestion(QuestionEditVM questionEditVM) {
        QueryWrapper<QuestionEditVM> wrapper = new QueryWrapper<>();
        QuestionEditVMMapper baseMapper = this.baseMapper;
        int res = baseMapper.updateQuestion(questionEditVM);
        return res;
    }
}
