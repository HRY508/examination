package com.examination.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 句体的选项抽象为一个类
 * @Author he
 * @Data 2022/10/5 0:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionItemObject {

    private String prefix;

    private String content;

    private Integer score;

    private String itemUuid;
}
