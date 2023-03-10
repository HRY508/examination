package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.viewmodel.PaperDetailsVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/15 16:58
 */
@Mapper
@Repository
public interface PaperDetailsVMMapper extends BaseMapper<PaperDetailsVM> {

      String queryNum="SELECT pd.pd_id, pd.num , p.`single_select` ,p.more_select ,q.`question_type`,q.score,q.correct,c.`content`, p.end_time " +
              "FROM t_papaer_details pd " +
              "LEFT JOIN t_paper p ON pd.`p_id`=p.`p_id` " +
              "LEFT JOIN t_question q ON pd.`q_id`=q.id " +
              "LEFT JOIN t_content c ON pd.`q_id`=c.id " +
              "where pd.num = #{num} and pd.p_id = #{pId} and pd.q_id = #{qId}";

      @Transactional(readOnly = true)
      @Select(queryNum)
      PaperDetailsVM getOneByPIdAndNum(@Param("pId") int pId,@Param("num") int num, @Param("qId") int qId);
}
