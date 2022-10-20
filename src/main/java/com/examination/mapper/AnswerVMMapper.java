package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.AnswerVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/20 20:16
 */
@Mapper
@Repository
public interface AnswerVMMapper extends BaseMapper<AnswerVM> {

    //通用的
    String selectList ="SELECT a.id,a.`p_id`,a.`user_id`,a.`pd_id`,a.`value`,a.`checked`,\n" +
            " u.user_name,u.real_name,u.u_id,p.p_name,pd.num\n" +
            " FROM t_answer a\n" +
            " LEFT JOIN  t_user u ON a.`user_id`= u.id\n" +
            " LEFT JOIN  t_paper p ON p.p_id = a.`p_id`\n" +
            " LEFT JOIN  t_papaer_details pd ON pd.pd_id = a.`pd_id`";

    @Select(selectList)
    Page<AnswerVM>  queryList(Page page);

    @Select(selectList+" where a.p_id = #{pId}")
    Page<AnswerVM> queryListByPId(Page page,@Param("pId") Integer pId);

}
