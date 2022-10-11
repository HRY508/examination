package com.examination.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/11 20:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_question_practice_detail")
public class QuestionPracticeDetails {

    private Integer id;

    private Integer questionId;

    private Integer userId;

    private Integer result;

}
