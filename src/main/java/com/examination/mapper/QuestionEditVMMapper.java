package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.QuestionEditVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionEditVMMapper extends BaseMapper<QuestionEditVM> {

    String selectQuestionEdit = "select q.id,q.question_type  questionType,q.score,q.difficult,q.correct,q.update_time,q.create_user createUser,q.create_time,q.status,c.content content from t_question q,t_content c where q.id = c.id and q.id = #{questionId}";

    @Select(selectQuestionEdit)
    QuestionEditVM selectByConditionQuestionVM(Integer questionId);


}
