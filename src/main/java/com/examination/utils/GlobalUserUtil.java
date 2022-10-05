package com.examination.utils;

import com.examination.bean.User;
import org.apache.shiro.SecurityUtils;

/**
 * @Description
 * @Author he
 * @Data 2022/10/4 16:29
 */

public abstract class GlobalUserUtil {
    public static User getUser(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
