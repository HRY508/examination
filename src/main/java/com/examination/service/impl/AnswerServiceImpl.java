package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.Answer;
import com.examination.mapper.AnswerMapper;
import com.examination.service.AnswerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/17 9:15
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {
    @Resource
    private AnswerMapper answerMapper;

    @Override
    public String ishave(Integer pdId) {
        String checked = "";
        LambdaUpdateWrapper<Answer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Answer::getPdId , pdId);
        Answer answer = answerMapper.selectOne(lambdaUpdateWrapper);
        if (answer != null){
            checked = answer.getChecked();
        }
        return checked;
    }

    @Override
    public Integer getTotalScore(Integer pId, Integer id) {
        LambdaQueryWrapper<Answer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Answer::getUserId , id).eq(Answer::getPId , pId);
        List<Answer> answers = answerMapper.selectList(queryWrapper);
        Integer totalScore = 0;

        for (Answer answer : answers) {
            totalScore = totalScore + answer.getValue();
        }
        return totalScore;
    }
}
