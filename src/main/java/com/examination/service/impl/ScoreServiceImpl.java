package com.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.Score;
import com.examination.mapper.ScoreMapper;
import com.examination.service.ScoreService;
import org.springframework.stereotype.Service;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/18 15:58
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper , Score> implements ScoreService {
}
