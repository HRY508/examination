package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.QuestionDetails;
import com.examination.mapper.QuestionDetailsMapper;
import com.examination.service.QuestionDetailsService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author he
 * @Data 2022/10/14 20:00
 */
@Service
public class QuestionDetailsServiceImpl extends ServiceImpl<QuestionDetailsMapper,QuestionDetails> implements QuestionDetailsService {
}
