package com.examination.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 题型种类
 * @Author he
 * @Data 2022/10/20 8:43
 */
@TableName(value = "t_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    private Integer id;

    private Integer qPool;

    private String qType;
}
