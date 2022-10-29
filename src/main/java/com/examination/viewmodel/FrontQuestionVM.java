package com.examination.viewmodel;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/11 19:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontQuestionVM {

    private Integer id;

    private Integer questionType;

    private String content;

    private Integer result;  // 0是未作过 1 是做过没对  2是做了对了

    private Integer difficult;

    private Integer solutioned;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime time;


}
