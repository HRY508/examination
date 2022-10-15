package com.examination.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户提交答案 每道题选的答案存入数据库
 * @Author:晓风残月Lx
 * @Date: 2022/10/13 17:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_answer")
public class Answer {

    private Integer id;

    private Integer user_id;

    private Integer PdId;

    private Integer value;

    private String checked;

}
