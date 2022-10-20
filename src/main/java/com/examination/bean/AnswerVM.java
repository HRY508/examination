package com.examination.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/20 20:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVM {

    private Integer id;

    private Integer userId;

    private Integer pId;

    private Integer PdId;

    private Integer value;

    private String checked;

    private String userName;

    private String uId;

    private String realName;

    private String pName;

    private Integer num;
}
