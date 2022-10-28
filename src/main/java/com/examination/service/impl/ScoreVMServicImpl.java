package com.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.ScoreVM;
import com.examination.mapper.ScoreVMMapper;
import com.examination.service.ScoreService;
import com.examination.service.ScoreVMService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/19 22:30
 */
@Service
public class ScoreVMServicImpl extends ServiceImpl<ScoreVMMapper, ScoreVM> implements ScoreVMService {
    @Resource
    ScoreVMMapper scoreVMMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ScoreVM> searchMark(Integer pId) {
        List<ScoreVM> scoreVMS = scoreVMMapper.searchMark(pId);
        Integer i = 1;
        for (ScoreVM scoreVM : scoreVMS) {
            scoreVM.setId(i);
            i++;
        }
        return scoreVMS;
    }
}
