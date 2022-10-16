package com.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.PaperDetails;
import com.examination.mapper.PaperDetailsMapper;
import com.examination.service.PaperDetailsService;
import org.springframework.stereotype.Service;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/15 21:40
 */
@Service
public class PaperDetailsServiceImpl extends ServiceImpl<PaperDetailsMapper, PaperDetails> implements PaperDetailsService {
}
