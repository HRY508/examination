package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.Content;
import com.examination.bean.FrontQuestionVM;
import com.examination.bean.QuestionPracticeDetails;
import com.examination.bean.QuestionVM;
import com.examination.mapper.FrontQuestionVMMapper;
import com.examination.mapper.QuestionVMMapper;
import com.examination.service.FrontQuestionVMService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/11 20:07
 */
@Service
public class FrontQuestionVMServiceImpl extends ServiceImpl<FrontQuestionVMMapper,FrontQuestionVM> implements FrontQuestionVMService {

    private FrontQuestionVMMapper frontQuestionVMMapper;


    @Override
    public Page<FrontQuestionVM> getList(Page page,Integer userId) {
        QueryWrapper<FrontQuestionVM> wrapper = new QueryWrapper<>();
        FrontQuestionVMMapper baseMapper = this.baseMapper;
        Page<FrontQuestionVM> FrontquestionVM = baseMapper.selectAllFrontQuestionVM(page,userId);
        return FrontquestionVM;
    }

    @Override
    public Page<FrontQuestionVM> getListByName(Page page, Integer userId, String searchName) {
        QueryWrapper<FrontQuestionVM> wrapper = new QueryWrapper<>();
        FrontQuestionVMMapper baseMapper = this.baseMapper;
        Page<FrontQuestionVM> frontQuestionVMPage = baseMapper.selectFrontQuestionVMByName(page, userId, searchName );
        return frontQuestionVMPage;
    }

    @Override
    public Page<FrontQuestionVM> getListById(Page page, Integer userId, Integer searchId) {
        QueryWrapper<FrontQuestionVM> wrapper = new QueryWrapper<>();
        FrontQuestionVMMapper baseMapper = this.baseMapper;
        Page<FrontQuestionVM> frontQuestionVMPage = baseMapper.selectFrontQuestionVMById(page, userId, searchId);
        return frontQuestionVMPage;
    }

    @Override
    public Page<FrontQuestionVM> getListByList(Page page, Integer searchId, String searchName,Integer userId) {
        return null;
    }
}
