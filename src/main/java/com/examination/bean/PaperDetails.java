package com.examination.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 卷子详细表
 * @Author:晓风残月Lx
 * @Date: 2022/10/13 17:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_papaer_details")
public class PaperDetails {
    @TableId(type = IdType.AUTO,value = "pd_id")
    private Integer pdId;

    private Integer pId;

    private Integer qId; // 和题库的题对应

    private Integer num; // 新生成的题号

}
