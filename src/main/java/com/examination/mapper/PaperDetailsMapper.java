package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.PaperDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/15 21:38
 */
@Mapper
@Repository
public interface PaperDetailsMapper extends BaseMapper<PaperDetails> {
}
