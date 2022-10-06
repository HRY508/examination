package com.examination.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.QuestionVM;
import com.examination.mapper.QuestionVMMapper;
import com.examination.service.QuestionVMService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author he
 * @Data 2022/10/5 22:31
 */
@Service
public class QuestionVMServiceImpl extends ServiceImpl<QuestionVMMapper, QuestionVM> implements QuestionVMService {

    @Override
    public Page<QuestionVM> getQuestionList(Page page) {
        QueryWrapper<QuestionVM> wrapper = new QueryWrapper<>();
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper.selectAllQuestionVM(page,wrapper);
        return questionVM;
    }

    @Override
    public Page<QuestionVM> selectByQuestionType(Page page, Integer questionType) {
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper
                .selectByQuestionType(page,questionType);
        return questionVM;
    }

    @Override
    public Page<QuestionVM> selectByQuestionName(Page page, String questionName) {
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper
                .selectByQuestionName(page,questionName);
        return questionVM;
    }

    @Override
    public Page<QuestionVM> selectByConditionQuestionVM(Page page, Integer questionType, String questionName) {
        QuestionVMMapper baseMapper = this.baseMapper;
        Page<QuestionVM> questionVM = baseMapper
                .selectByConditionQuestionVM(page,questionType,questionName);
        return  questionVM;
    }

}
