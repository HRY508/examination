package com.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.examination.bean.ScoreVM;

import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/19 22:31
 */
public interface ScoreVMService extends IService<ScoreVM> {
    List<ScoreVM> searchMark(Integer pId);
}
