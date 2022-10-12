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

    String queryList = "select c.id id,c.content content,tqpd.result result,q.difficult difficult ,q.question_type questionType  from t_content c " +
            "left join t_question_practice_detail tqpd on c.id = tqpd.question_id and tqpd.user_id =#{userId}" +
            " left join t_question q on c.id = q.id";

    String queryById = queryList + " where c.id = #{qId}";

    String queryByName = queryList +" where c.content like concat('%',#{qName},'%')";

    @Select(queryList)
    Page<FrontQuestionVM> selectAllFrontQuestionVM(Page page, @Param("userId") Integer userId);

    @Select(queryById)
    Page<FrontQuestionVM> selectFrontQuestionVMById(Page page, @Param("userId") Integer userId, @Param("qId") Integer qId );

    @Select(queryByName)
    Page<FrontQuestionVM> selectFrontQuestionVMByName(Page page, @Param("userId") Integer userId, @Param("qName") String qName );


}
