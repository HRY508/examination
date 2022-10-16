package com.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.PaperDetailsVM;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/15 17:22
 */

public interface PaperDetailsVMService extends IService<PaperDetailsVM> {
    PaperDetailsVM getOneByPIdAndNum(Integer pId, Integer num);
}
