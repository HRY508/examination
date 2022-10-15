package com.examination.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 分数表 存入每次考试的成绩
 * @Author:晓风残月Lx
 * @Date: 2022/10/13 17:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_score")
public class Score {

    private Integer sId;

    private Integer userId; // 用户id

    private Integer pId; // 卷子id

    private Integer mark; // 分数

    private LocalDateTime examTime;

    private LocalDateTime createTime;
}
