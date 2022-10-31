package com.examination.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer qPool;

    private String qType;
}
