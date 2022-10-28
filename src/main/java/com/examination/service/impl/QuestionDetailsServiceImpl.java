package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.QuestionDetails;
import com.examination.mapper.QuestionDetailsMapper;
import com.examination.service.QuestionDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @Description
 * @Author he
 * @Data 2022/10/14 20:00
 */
@Service
public class QuestionDetailsServiceImpl extends ServiceImpl<QuestionDetailsMapper,QuestionDetails> implements QuestionDetailsService {
    @Resource
    QuestionDetailsMapper questionDetailsMapper;

    @Override
    @Transactional(readOnly = true)
    public Integer getFinishNum() {
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        LambdaQueryWrapper<QuestionDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(QuestionDetails::getCreateTime,today_start,today_end);
        return questionDetailsMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getrightNum() {
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        LambdaQueryWrapper<QuestionDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QuestionDetails::getResult,2).between(QuestionDetails::getCreateTime,today_start,today_end);
        return questionDetailsMapper.selectCount(queryWrapper);
    }
}
