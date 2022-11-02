package com.examination.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 卷子表
 * @Author:晓风残月Lx
 * @Date: 2022/10/13 17:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_paper")
public class Paper {
    @TableId(type = IdType.AUTO ,value = "p_id")
    private Integer pId;

    private String pName;

    private Integer pStatus;  // 启用禁用

    private Integer pdStatus;// 卷子状态 0未开始 1开放 2已结束

    private Integer isAuto;

    private Integer singleSelect;

    private Integer moreSelect;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
