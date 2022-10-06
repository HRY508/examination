package com.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.QuestionVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionVMMapper extends BaseMapper<QuestionVM> {
    //全部查询
    String selectAllSql = "select q.id,q.question_type  questionType,q.score,q.difficult,q.create_user createUser,q.create_time,q.status,c.content from t_question q,t_content c where q.id = c.id ${ew.customSqlSegment}";

    @Select(selectAllSql)
    Page<QuestionVM> selectAllQuestionVM(Page page, @Param("ew") QueryWrapper<QuestionVM> queryWrapper);

}
