package com.examination.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/18 21:20
 */
public class LocalDateUtil {

    public static String localDateToString(LocalDateTime time){
        // 具有转换功能的对象
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String localTime = df.format(time);

        return localTime;

    }

}
