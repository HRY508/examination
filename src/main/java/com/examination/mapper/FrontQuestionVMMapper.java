package com.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.FrontQuestionVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/11 20:10
 */
@Mapper
@Repository
public interface FrontQuestionVMMapper extends BaseMapper<FrontQuestionVM> {

    String queryList = "select c.id id,c.content content,tqpd.result result,q.solutioned solutioned,q.difficult difficult,q.question_type questionType,q.question_pool questionPool from t_content c " +
            "left join t_question_practice_detail tqpd on c.id = tqpd.question_id and tqpd.user_id =#{userId}" +
            " left join t_question q on c.id = q.id";

    String queryById = queryList + " where c.id = #{qId} order BY `id` ASC";

    String queryByName = queryList +" where c.content like concat('%',#{qName},'%') order BY `id` ASC";

    String queryByPool = queryList +" where q.question_pool = #{questionPool}";

    @Select(queryList+"  order BY `id` ASC")
    Page<FrontQuestionVM> selectAllFrontQuestionVM(Page page, @Param("userId") Integer userId);

    @Select(queryById)
    Page<FrontQuestionVM> selectFrontQuestionVMById(Page page, @Param("userId") Integer userId, @Param("qId") Integer qId );

    @Select(queryByName)
    Page<FrontQuestionVM> selectFrontQuestionVMByName(Page page, @Param("userId") Integer userId, @Param("qName") String qName );

    @Select(queryByPool)
    Page<FrontQuestionVM> selectFrontQuestionVMByPool(Page page, @Param("userId") Integer userId, @Param("questionPool") Integer questionPool );

}
