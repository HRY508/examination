package com.examination.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/15 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDetailsVM {

    private Integer pdId;

    private Integer num; // 新生成的题号

    private String content;

    private Integer singleSelect;

    private Integer moreSelect;

    private Integer questionType; // 单选 1  多选 2

    private Integer score; //分值

    private String correct; // 正确答案
}
