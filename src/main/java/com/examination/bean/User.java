package com.examination.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
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

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String password;

    private String uId;

    private String realName;

    private Integer sex;

    private String profession;

    private String perms;

    private Integer uStatus;

    private Date createTime;
}
