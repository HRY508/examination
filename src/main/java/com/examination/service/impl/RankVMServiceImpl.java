package com.examination.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.RankVM;
import com.examination.bean.User;
import com.examination.mapper.RankVMMapper;
import com.examination.service.QuestionDetailsService;
import com.examination.service.RankVMService;
import com.examination.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/22 19:29
 */
@Service
public class RankVMServiceImpl extends ServiceImpl<RankVMMapper, RankVM> implements RankVMService {
    @Resource
    RankVMMapper rankVMMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RankVM> getList(Page page) {
        Page<RankVM> rankVMPage = rankVMMapper.queryList(page);
        List<RankVM> records = rankVMPage.getRecords();
        Integer i = 1;
        for (RankVM record : records) {
            records.get(i-1).setRId(i);
            i++;
        }
        rankVMPage.setRecords(records);
        return rankVMPage;
    }
}
