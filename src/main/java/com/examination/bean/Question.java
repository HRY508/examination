package com.examination.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description
 * @Author he
 * @Data 2022/10/4 15:46
 */
@TableName(value = "t_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @TableId(type = IdType.AUTO,value = "id")
    private Integer id;

    private Integer questionType;

    private Integer score;

    private Integer difficult;

    private String correct;

    private Integer solutioned;

    private String createUser;

    private Integer status;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
