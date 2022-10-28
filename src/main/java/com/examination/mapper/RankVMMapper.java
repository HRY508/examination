package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.RankVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/22 19:27
 */
@Mapper
@Repository
public interface RankVMMapper extends BaseMapper<RankVM> {

    @Select("SELECT \n" +
            " u.`id`,u.`user_name`,u.`real_name`,u.`profession`,(SELECT COUNT(result) resultNum FROM t_question_practice_detail pd WHERE u.`id`= pd.user_id AND pd.result = 2) resultNum\n" +
            "FROM t_user u ORDER BY resultNum DESC")
    @Transactional(readOnly = true)
    Page<RankVM>  queryList(Page page);

}
