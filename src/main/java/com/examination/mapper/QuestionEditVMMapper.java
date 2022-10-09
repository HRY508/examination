package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.QuestionEditVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionEditVMMapper extends BaseMapper<QuestionEditVM> {

    String selectQuestionEdit = "select q.id,q.question_type  questionType,q.score,q.difficult,q.correct,q.update_time,q.create_user createUser,q.create_time,q.status,c.content content from t_question q,t_content c where q.id = c.id and q.id = #{questionId}";

    String updateQuestion = "update t_question q,t_content c set q.question_type = #{qt.questionType},q.score = #{qt.score},q.correct=#{qt.correct},q.difficult=#{qt.difficult},c.content= #{qt.content},q.create_user = #{qt.createUser},c.content = #{qt.content} where q.id = c.id and q.id = #{qt.id}";
//                                                                                                                                                                                                              from t_question q,t_content c where q.id = c.id and q.id = #{qt.id}"
    //查询
    @Select(selectQuestionEdit)
    QuestionEditVM selectByConditionQuestionVM(Integer questionId);

    //更新
    @Update(updateQuestion)
    int updateQuestion(@Param("qt") QuestionEditVM questionEditVM);
}
