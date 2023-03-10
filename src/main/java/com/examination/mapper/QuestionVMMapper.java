package com.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.viewmodel.QuestionVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Mapper
public interface QuestionVMMapper extends BaseMapper<QuestionVM> {
    //固定开头
    String start = "SELECT q.id,q.question_type  questionType,q.score,q.difficult,q.solutioned solutioned,q.create_user createUser,q.create_time,q.status,c.content,t.`q_type`,q.`question_pool`\n" +
            "FROM t_question q,t_content c,t_type t WHERE q.id = c.id AND t.`q_pool`=q.`question_pool`  ";
    //固定结尾
    String end = "${ew.customSqlSegment}";

    //全部查询
    @Transactional(readOnly = true)
    @Select(start+end)
    Page<QuestionVM> selectAllQuestionVM(Page page, @Param("ew") QueryWrapper<QuestionVM> queryWrapper);


    //题目关键字查询
    @Transactional(readOnly = true)
    @Select(start+"and c.content like concat('%',#{questionName},'%')")
    Page<QuestionVM> selectByQuestionName(Page page, @Param("questionName")String questionName);

    //通过题型查询
    @Transactional(readOnly = true)
    @Select(start+"and q.question_type = #{questionType}")
    Page<QuestionVM> selectByQuestionType(Page page, @Param("questionType")Integer questionType);

    //通过题型、关键字查询，使用拼接sql自定义sql语句,${}有sql注入的风险
    @Transactional(readOnly = true)
    @Select(start+"and q.question_type = #{questionType} and c.content like concat('%',#{questionName},'%')")
    Page<QuestionVM> selectByConditionQuestionVM(Page page,
                                                 @Param("questionType")Integer questionType,
                                                 @Param("questionName") String questionName);
    //通过题型、题目种类查询
    @Transactional(readOnly = true)
    @Select(start+"and q.question_type = #{questionType} and q.question_pool = #{questionPool}")
    Page<QuestionVM> selectByQuestionTypeAndQuestionPool(Page page,
                                                 @Param("questionType")Integer questionType,
                                                 @Param("questionPool") Integer questionPool);

    //通过题目种类查询
    @Transactional(readOnly = true)
    @Select(start+"and q.question_pool = #{questionPool}")
    Page<QuestionVM> selectByQuestionPool(Page page, @Param("questionPool") Integer questionPool);

    //全部条件搜索
    @Transactional(readOnly = true)
    @Select(start+"and q.question_type = #{questionType} q.question_pool = #{questionPool} and c.content like concat('%',#{questionName},'%') " )
    Page<QuestionVM> selectByAllConditionQuestionVM(Page page,
                                                    @Param("questionName") String questionName,
                                                    @Param("questionType")Integer questionType,
                                                    @Param("questionPool") Integer questionPool);

    @Transactional(readOnly = true)
    @Select(start+ " and  q.question_pool = #{questionPool} and c.content like concat('%',#{questionName},'%') ")
    Page<QuestionVM> selectByQuestionPoolAndName(Page page,  @Param("questionName") String questionName, @Param("questionPool") Integer questionPool);

    @Transactional(readOnly = true)
    @Select(start+ " and  q.question_type = #{questionType} and c.content like concat('%',#{questionName},'%') ")
    Page<QuestionVM> selectByQuestionTypeAndQuestionName(Page page, @Param("questionName") String questionName,  @Param("questionType")Integer questionType);

    @Select(start+ " and  q.question_pool = #{questionPool}")
    List<QuestionVM> selectByQPool(Integer pool);
}
