package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.PaperDetailsVM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/15 16:58
 */
@Mapper
@Repository
public interface PaperDetailsVMMapper extends BaseMapper<PaperDetailsVM> {

      String sql="SELECT pd.num , p.`single_select` ,p.more_select ,q.`question_type`,q.score,\n" +
            "\tq.correct,c.`content`\n" +
            "FROM t_papaer_details pd\n" +
            "LEFT JOIN t_paper p ON pd.`p_id`=p.`p_id`\n" +
            "LEFT JOIN t_question q ON pd.`q_id`=q.id\n" +
            "LEFT JOIN t_content c ON pd.`q_id`=c.id";

}
