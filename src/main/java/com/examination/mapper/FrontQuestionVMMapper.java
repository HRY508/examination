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

    String querryList ="select c.id id,c.content content,tqpd.result result,q.difficult difficult ,q.question_type questionType  from t_content c " +
            "left join t_question_practice_detail tqpd on c.id = tqpd.question_id and tqpd.user_id =#{userId}" +
            " left join t_question q on c.id = q.id";

    @Select(querryList)
    Page<FrontQuestionVM> selectAllFrontQuestionVM(Page page, QueryWrapper<FrontQuestionVM> wrapper, @Param("userId") Integer userId);
}
