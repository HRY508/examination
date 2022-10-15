package com.examination.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author he
 * @Data 2022/10/14 19:54
 */
@TableName(value = "t_question_practice_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDetails {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer questionId;

    private Integer result;
}
