package com.examination.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.AnswerVM;
import com.examination.bean.QuestionVM;
import com.examination.mapper.AnswerVMMapper;
import com.examination.service.AnswerService;
import com.examination.service.AnswerVMService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/20 20:20
 */
@Service
public class AnswerVMServiceImpl extends ServiceImpl<AnswerVMMapper, AnswerVM> implements AnswerVMService {

    @Resource
    private AnswerVMMapper answerVMMapper;

    @Override
    public Page<AnswerVM> selectList(Page page) {
        return answerVMMapper.queryList(page);
    }

    @Override
    public Page<AnswerVM> selectListByPId(Page page, Integer pId) {
        return answerVMMapper.queryListByPId(page,pId);
    }
}
