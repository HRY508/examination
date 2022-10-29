package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.PaperDetails;
import com.examination.viewmodel.PaperDetailsVM;
import com.examination.mapper.PaperDetailsMapper;
import com.examination.mapper.PaperDetailsVMMapper;
import com.examination.service.PaperDetailsVMService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 查询下一题或上一题内容
     * @param pId
     * @param num
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PaperDetailsVM getOneByPIdAndNum(Integer pId, Integer num) {
        LambdaQueryWrapper<PaperDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PaperDetails::getNum , num).eq(PaperDetails::getPId,pId);
        PaperDetails paperDetails = paperDetailsMapper.selectOne(queryWrapper);
        PaperDetailsVM oneByPIdAndNum = paperDetailsVMMapper.getOneByPIdAndNum(pId, num, paperDetails.getQId());
        return oneByPIdAndNum;
    }

}
