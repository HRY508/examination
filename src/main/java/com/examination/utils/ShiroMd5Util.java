package com.examination.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Description
 * @Author he
 * @Data 2022/10/1 15:12
 */

public class ShiroMd5Util {
    //添加user的密码加密方法
    public static String  SysMd5(String username,String password) {
        String hashAlgorithmName = "MD5";//加密方式
        ByteSource salt = ByteSource.Util.bytes(username);//以账号作为盐值
        int hashIterations = 1024;//加密1024次
        String newPassword= new SimpleHash(hashAlgorithmName,password,salt,hashIterations).toHex();
        return newPassword;
    }
}

