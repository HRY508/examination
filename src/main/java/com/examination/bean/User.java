package com.examination.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author he
 * @Data 2022/9/27 0:35
 */
@TableName(value = "t_user")
@Data
//序列化操作，配置cookie操作时要求
public class User implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String userName;

    private String password;

    private String uId;

    private String realName;

    private Integer sex;

    private String profession;

    private String perms;

    private Integer status;

    private Date createTime;
}
