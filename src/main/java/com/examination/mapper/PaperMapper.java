package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/15 17:17
 */
@Mapper
@Repository
public interface PaperMapper extends BaseMapper<Paper> {
}
