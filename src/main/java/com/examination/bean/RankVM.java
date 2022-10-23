package com.examination.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/22 19:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankVM {
    @TableId(type = IdType.AUTO)
    private Integer rId;

    private Integer id;

    private String userName;

    private String profession;

    private Integer resultNum;
}
