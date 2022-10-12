package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.QuestionPracticeDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/12 18:47
 */
@Mapper
@Repository
public interface QuestionPracticeDetailsMapper extends BaseMapper<QuestionPracticeDetails> {
}
