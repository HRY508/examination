package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.Paper;
import com.examination.bean.PaperDetails;
import com.examination.bean.PaperDetailsVM;
import com.examination.mapper.PaperDetailsMapper;
import com.examination.mapper.PaperDetailsVMMapper;
import com.examination.mapper.PaperMapper;
import com.examination.service.PaperDetailsService;
import com.examination.service.PaperDetailsVMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/15 17:00
 */
@Service
public class PaperDetailsVMServiceImpl extends ServiceImpl<PaperDetailsVMMapper, PaperDetailsVM> implements PaperDetailsVMService {
    @Resource
    private PaperDetailsVMMapper paperDetailsVMMapper;
    @Resource
    private PaperDetailsMapper paperDetailsMapper;

    @Override
    public PaperDetailsVM getOneByPIdAndNum(Integer pId, Integer num) {
        LambdaQueryWrapper<PaperDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PaperDetails::getNum , num);
        PaperDetails paperDetails = paperDetailsMapper.selectOne(queryWrapper);
        PaperDetailsVM oneByPIdAndNum = paperDetailsVMMapper.getOneByPIdAndNum(pId, num, paperDetails.getQId());
        return oneByPIdAndNum;
    }
}
