package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.Score;
import com.examination.mapper.ScoreMapper;
import com.examination.service.ScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jnlp.ServiceManager;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/18 15:58
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper , Score> implements ScoreService {

    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public int selectByPId(Integer pId, Integer userId) {
        LambdaQueryWrapper<Score> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Score::getPId,pId).eq(Score::getUserId,userId);
        Score score = scoreMapper.selectOne(queryWrapper);
        if (score != null){
            return score.getMark();
        }
        return 0;
    }
}
