package com.examination.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.viewmodel.QuestionVM;
import com.examination.mapper.QuestionVMMapper;
import com.examination.service.QuestionVMService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author he
 * @Data 2022/10/5 22:31
 */
@Service
public class QuestionVMServiceImpl extends ServiceImpl<QuestionVMMapper, QuestionVM> implements QuestionVMService {

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionVM> getQuestionList(Page page) {
        QueryWrapper<QuestionVM> wrapper = new QueryWrapper<>();
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper.selectAllQuestionVM(page,wrapper);
        return questionVM;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionVM> selectByQuestionType(Page page, Integer questionType) {
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper
                .selectByQuestionType(page,questionType);
        return questionVM;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionVM> selectByQuestionName(Page page, String questionName) {
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper
                .selectByQuestionName(page,questionName);
        return questionVM;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionVM> selectByConditionQuestionVM(Page page, Integer questionType, String questionName) {
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper
                .selectByConditionQuestionVM(page,questionType,questionName);
        return  questionVM;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionVM> selectByQuestionTypeAndQuestionPool(Page page, Integer questionType, Integer questionPool) {
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper
                .selectByQuestionTypeAndQuestionPool(page,questionType,questionPool);
        return  questionVM;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionVM> selectByQuestionPool(Page page, Integer questionPool) {
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper
                .selectByQuestionPool(page,questionPool);
        return  questionVM;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionVM> selectByAllConditionQuestionVM(Page page, String questionName, Integer questionType, Integer questionPool) {
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper
                .selectByAllConditionQuestionVM(page,questionName,questionType,questionPool);
        return  questionVM;
    }

}
