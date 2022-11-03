package com.examination.bean;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description
 * @Author he
 * @Data 2022/9/27 0:35
 */
@TableName(value = "t_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String password;

    private String uId;

    private String realName;

    private Integer sex;

    private String profession;

    private String perms;

    private Integer uStatus;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;



}
