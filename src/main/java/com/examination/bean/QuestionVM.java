package com.examination.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


/**
 * @Description 查询题库时展示的视图类
 * @Author he
 * @Data 2022/10/4 22:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVM {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer questionType;

    private String content;

    private Integer score;

    private Integer difficult;

    private String createUser;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
