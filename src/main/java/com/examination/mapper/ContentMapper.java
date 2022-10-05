package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.Content;
import com.examination.bean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author he
 * @Data 2022/10/5 2:06
 */
@Repository
@Mapper
public interface ContentMapper extends BaseMapper<Content> {
}
