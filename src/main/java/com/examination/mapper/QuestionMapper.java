package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {


}
