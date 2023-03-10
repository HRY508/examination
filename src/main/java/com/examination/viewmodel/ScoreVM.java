package com.examination.viewmodel;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/19 22:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreVM {

    private Integer id;

    private String userName;

    private String uId;

    private String realName;

    private Integer mark; // 分数

    private String profession;

    private LocalDateTime examTime;

}
