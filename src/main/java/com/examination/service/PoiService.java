package com.examination.service;

import com.examination.viewmodel.ScoreVM;

import java.io.IOException;
import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/20 8:30
 */

public interface PoiService {

    /**
     * 导出
     */
    void downLoadExcel(List<ScoreVM> scoreVMList) throws IOException;

}
