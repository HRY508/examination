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

/**
 * @Description
 * @Author he
 * @Data 2022/10/7 16:06
 */
@Service
public class QuestionEditVMServiceImpl extends ServiceImpl<QuestionEditVMMapper, QuestionEditVM> implements QuestionEditVMService {
    @Override
    public QuestionEditVM selectByConditionQuestionVM(Integer questionId) {
        QueryWrapper<QuestionEditVM> wrapper = new QueryWrapper<>();
        QuestionEditVMMapper baseMapper = this.baseMapper;
        QuestionEditVM questionEditVM = baseMapper.selectByConditionQuestionVM(questionId);
        return questionEditVM;
    }
}
