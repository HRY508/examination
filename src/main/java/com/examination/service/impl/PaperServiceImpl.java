package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.Paper;
import com.examination.mapper.PaperMapper;
import com.examination.service.PaperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/15 17:18
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService {

}
