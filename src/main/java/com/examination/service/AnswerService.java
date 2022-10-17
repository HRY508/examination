package com.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.Answer;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/17 9:14
 */

public interface AnswerService extends IService<Answer> {
    String ishave(Integer pdId);
}
