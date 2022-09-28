package com.examination.bean;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author he
 * @Data 2022/9/27 0:35
 */
@TableName(value = "t_user")
@Data
public class User {

    @TableId(value = "id")
    private Long id;

    private String userUuid;

    private String userName;

    private String password;

    private String realName;

    private Integer age;

    private Integer sex;

    private Date birthDay;

    private String perms;

    private String phone;

    private Integer role;

    private Integer status;

    private String imagePath;

    private Date createTime;
}
