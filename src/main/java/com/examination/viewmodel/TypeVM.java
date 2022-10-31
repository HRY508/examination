package com.examination.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/29 17:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeVM {

    private Integer id;

    private Integer qPool;

    private String qType;

    private Integer qNumber;

}
