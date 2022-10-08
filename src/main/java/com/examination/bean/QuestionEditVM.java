package com.examination.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description
 * @Author he
 * @Data 2022/10/4 22:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEditVM {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer questionType;

    private String content;

    private Integer score;

    private String correct;

    private Integer difficult;

    private String createUser;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
