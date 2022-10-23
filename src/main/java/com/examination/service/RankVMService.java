package com.examination.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.RankVM;

import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/22 19:28
 */
public interface RankVMService extends IService<RankVM> {
    Page<RankVM> getList(Page page);
}
