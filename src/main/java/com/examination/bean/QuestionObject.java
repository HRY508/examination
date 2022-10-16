package com.examination.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 具体的题目抽象为一个类
 * @Author he
 * @Data 2022/10/5 0:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionObject {
    private String titleContent;

    private String analyze;

    private List<QuestionItemObject> questionItemObjects;

}
