package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.examination.bean.ScoreVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/19 22:30
 */
@Mapper
@Repository
public interface ScoreVMMapper extends BaseMapper<ScoreVM> {

    String query = "SELECT s.`s_id`,u.`user_name`,u.`u_id`,u.`real_name`,s.`mark`,s.`exam_time`\n" +
            "FROM t_score s\n" +
            "LEFT JOIN t_user u ON u.`id` = s.`user_id`\n" +
            "WHERE s.`p_id`= #{pId} order by s.`mark` DESC";

    @Select(query)
    List<ScoreVM> searchMark(@Param("pId")Integer pId);

}
