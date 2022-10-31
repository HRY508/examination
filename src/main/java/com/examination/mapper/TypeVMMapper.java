package com.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.viewmodel.TypeVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/29 17:56
 */
@Mapper
@Repository
public interface TypeVMMapper extends BaseMapper<TypeVM> {

    String query ="SELECT *,\n" +
            "(SELECT COUNT(*) FROM t_question q WHERE q.`question_pool`= t.`q_pool`) q_number\n" +
            "FROM t_type t ";


    @Select(query)
    Page<TypeVM> queryByPage(Page<TypeVM> page);

    @Select(query+" where t.`q_type` like concat('%',#{searchName},'%')")
    Page<TypeVM> queryByPageAndSearchName(Page<TypeVM> page, @Param("searchName") String searchName);

}
