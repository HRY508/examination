package com.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.QuestionDetails;
import com.examination.mapper.QuestionDetailsMapper;

public interface QuestionDetailsService extends IService<QuestionDetails> {

    Integer getFinishNum();

    Integer getrightNum();
}
