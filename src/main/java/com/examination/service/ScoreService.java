package com.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.Score;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/18 15:58
 */
public interface ScoreService extends IService<Score> {
    int selectByPId(Integer pId, Integer userId);
}
